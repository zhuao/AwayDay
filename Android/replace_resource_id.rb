  def get_resource_map
    resource_id_map = {}
    resource_type = ""
    File.open("com/thoughtworks/mobile/awayday/R.java", "r").each_line do |line|
      if (line.include? "public static final class")
        resource_type = line.chomp.gsub!("public static final class", "").tr_s(" ", "")
      end
      if (line.include? "public static final int")

        resource_id = line.scan(/public static final int ([\w\-]+) = (\d{10})/)
        unless ( resource_id.nil? || resource_id.size <= 0)
          resource_name = "R." + resource_type + "." + resource_id[0][0]
          resource_id_map.merge!({resource_id[0][1] => resource_name })
        end
      end
    end
    resource_id_map
  end

  resource_map = get_resource_map
  Dir["**/*.java"].each do |filename|
    next if filename.include? "R.java"
    lines = File.readlines(filename).map { |line|
      resource_ids = line.scan(/\d{10}/)
      resource_ids.each {|resource_id|
        line.gsub!(resource_id, resource_map[resource_id]) unless resource_map[resource_id].nil?
      }
      line
    }
    File.open(filename, "w") do |file|
      file.puts lines
    end

  end
