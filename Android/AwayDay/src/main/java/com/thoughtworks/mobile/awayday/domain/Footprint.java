package com.thoughtworks.mobile.awayday.domain;

import android.net.Uri;
import com.thoughtworks.mobile.awayday.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class Footprint
        implements Serializable {
    public String content;
    public Date createdDate;
    public int id;
    public String imageUriString;
    public int sessionId;

    public Footprint() {
    }

    public Footprint(String paramString, int paramInt) {
        this.content = paramString;
        this.sessionId = paramInt;
    }

    public Uri imageUri() {
        if (StringUtils.isEmpty(this.imageUriString))
            return null;
        return Uri.parse(this.imageUriString);
    }

    public boolean isValid() {
        return (!StringUtils.isEmpty(this.content)) || (imageUri() != null);
    }

    public void removeImage() {
        this.imageUriString = "";
    }
}