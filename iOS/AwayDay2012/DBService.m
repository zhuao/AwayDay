//
//  DBService.m
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-10.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "DBService.h"
#import "AppDelegate.h"
#import "AppConstant.h"
#import "Session.h"
#import "Reminder.h"
#import "AppHelper.h"

@implementation DBService

#pragma mark - session list method
+(NSMutableArray *)getLocalAgendaList{
    NSMutableArray *result=[[NSMutableArray alloc]initWithCapacity:0];
    
    NSString *sql=@"select * from session_list order by session_start";
    
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    
    NSDateFormatter *formatter=[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss ZZZ"];
    
    sqlite3_stmt *stmt;
    sqlite3_prepare_v2(appDelegate.database, [sql UTF8String], -1, &stmt, NULL);
    while(sqlite3_step(stmt) == SQLITE_ROW){
        char *sessionID=(char *)sqlite3_column_text(stmt, 1);
        char *sessionTitle=(char *)sqlite3_column_text(stmt, 2);
        char *sessionDescription=(char *)sqlite3_column_text(stmt, 3);
        char *sessionSpeaker=(char *)sqlite3_column_text(stmt, 4);
        char *sessionStart=(char *)sqlite3_column_text(stmt, 5);
        char *sessionEnd=(char *)sqlite3_column_text(stmt, 6);
        char *sessionLocation=(char *)sqlite3_column_text(stmt, 7);
        
        Session *session=[[Session alloc]init];
        
        if(sessionID!=nil)[session setSessionID:[NSString stringWithUTF8String:sessionID]];
        if(sessionTitle!=nil)[session setSessionTitle:[NSString stringWithUTF8String:sessionTitle]];
        if(sessionDescription!=nil)[session setSessionNote:[NSString stringWithUTF8String:sessionDescription]];
        if(sessionSpeaker!=nil)[session setSessionSpeaker:[NSString stringWithUTF8String:sessionSpeaker]];
        if(sessionStart!=nil){
            NSString *timeStr=[NSString stringWithUTF8String:sessionStart];
            NSDate *start=[formatter dateFromString:timeStr];
            [session setSessionStartTime:start];
        }
        if(sessionEnd!=nil)[session setSessionEndTime:[formatter dateFromString:[NSString stringWithUTF8String:sessionEnd]]];
        if(sessionLocation!=nil)[session setSessionAddress:[NSString stringWithUTF8String:sessionLocation]];
        
        [result addObject:session];     
    }
    if(stmt)sqlite3_finalize(stmt);
    return result;
}

+(void)deleteAllSessions{
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *del=@"delete from session_list";
    sqlite3_stmt *stmt;
    char *errorMsg;
    if(sqlite3_exec(appDelegate.database, [del UTF8String], nil, &stmt, &errorMsg)!=SQLITE_OK){
        NSLog(@"%@", [NSString stringWithUTF8String:errorMsg]);
    }
}
+(void)saveSessionList:(NSArray *)sessionList{
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    sqlite3_stmt *stmt;
    char *errorMsg;
    for(Session *session in sessionList){
        NSString *title=[session.sessionTitle stringByReplacingOccurrencesOfString:@"'" withString:@"''"];
        NSString *note=[session.sessionNote stringByReplacingOccurrencesOfString:@"'" withString:@"''"];
        NSString *address=[session.sessionAddress stringByReplacingOccurrencesOfString:@"'" withString:@"''"];
        NSString *speaker=[session.sessionSpeaker stringByReplacingOccurrencesOfString:@"'" withString:@"''"];
        NSString *save=[NSString stringWithFormat:@"insert into session_list(session_id,session_title,session_description,session_speaker,session_start,session_end,session_location) values('%@','%@','%@','%@','%@','%@','%@')", [session sessionID], title, note,speaker, [session sessionStartTime], [session sessionEndTime], address];
        
        
        if(sqlite3_exec(appDelegate.database, [save UTF8String], nil, &stmt, &errorMsg)!=SQLITE_OK){
            NSLog(@"%@", [NSString stringWithUTF8String:errorMsg]);
        }
    }
}

+(NSMutableArray *)getSessionListBySessionIDList:(NSMutableArray *)list{
    if(list==nil || list.count==0) return nil;
    
    NSMutableArray *result=[[NSMutableArray alloc]initWithCapacity:0];
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *sql=@"select * from session_list where session_id in (";
    for(NSString *sid in list){
        sql=[sql stringByAppendingFormat:@"%@,",sid];
    }
    sql=[sql substringToIndex:sql.length-1];
    sql=[sql stringByAppendingString:@")"];
    
    NSDateFormatter *formatter=[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm ZZZ"];
    
    sqlite3_stmt *stmt;
    sqlite3_prepare_v2(appDelegate.database, [sql UTF8String], -1, &stmt, NULL);
    while(sqlite3_step(stmt) == SQLITE_ROW){
        char *sessionID=(char *)sqlite3_column_text(stmt, 1);
        char *sessionTitle=(char *)sqlite3_column_text(stmt, 2);
        char *sessionDescription=(char *)sqlite3_column_text(stmt, 3);
        char *sessionSpeaker=(char *)sqlite3_column_text(stmt, 4);
        char *sessionStart=(char *)sqlite3_column_text(stmt, 5);
        char *sessionEnd=(char *)sqlite3_column_text(stmt, 6);
        char *sessionLocation=(char *)sqlite3_column_text(stmt, 7);
        
        Session *session=[[Session alloc]init];
        
        if(sessionID!=nil)[session setSessionID:[NSString stringWithUTF8String:sessionID]];
        if(sessionTitle!=nil)[session setSessionTitle:[NSString stringWithUTF8String:sessionTitle]];
        if(sessionDescription!=nil)[session setSessionNote:[NSString stringWithUTF8String:sessionDescription]];
        if(sessionSpeaker!=nil)[session setSessionSpeaker:[NSString stringWithUTF8String:sessionSpeaker]];
        if(sessionStart!=nil)[session setSessionStartTime:[formatter dateFromString:[NSString stringWithUTF8String:sessionStart]]];
        if(sessionEnd!=nil)[session setSessionEndTime:[formatter dateFromString:[NSString stringWithUTF8String:sessionEnd]]];
        if(sessionLocation!=nil)[session setSessionAddress:[NSString stringWithUTF8String:sessionLocation]];
        
        [result addObject:session];
    }
    if(stmt)sqlite3_finalize(stmt);
    return result;
}

#pragma mark - reminder method
+(void)deleteUserReminder:(NSString *)sessionID{
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *del=[NSString stringWithFormat:@"delete from user_reminder where session_id='%@'", sessionID];
    sqlite3_stmt *stmt;
    sqlite3_exec(appDelegate.database, [del UTF8String], nil, &stmt, nil);
}

+(void)saveUserReminder:(NSString *)sessionID withMinutes:(int)min{
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *save=[NSString stringWithFormat:@"insert into user_reminder(session_id,reminder_min) values('%@',%d)", sessionID, min];
    sqlite3_stmt *stmt;
    
    char *errorMsg;
    if(sqlite3_exec(appDelegate.database, [save UTF8String], nil, &stmt, &errorMsg)!=SQLITE_OK){
        NSLog(@"%@", [NSString stringWithUTF8String:errorMsg]);
    }
}

+(NSMutableArray *)getAllUserReminder{
    NSMutableArray *result=[[NSMutableArray alloc]initWithCapacity:0];
    
    NSString *q=@"select * from user_reminder order by id";
    
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    sqlite3_stmt *stmt;
    sqlite3_prepare_v2(appDelegate.database, [q UTF8String], -1, &stmt, NULL);
    while(sqlite3_step(stmt) == SQLITE_ROW){
        char *sessionID=(char *)sqlite3_column_text(stmt, 1);
        int reminderMin=sqlite3_column_int(stmt, 2);
        
        Reminder *reminder=[[Reminder alloc]init];
        [reminder setSessionID:[NSString stringWithUTF8String:sessionID]];
        [reminder setReminderMinute:[NSNumber numberWithInt:reminderMin]];
        [result addObject:reminder];
    }
    if(stmt)sqlite3_finalize(stmt);
    return result;
}

+(Reminder *)getReminderBySessionID:(NSString *)sessionID{
    Reminder *result=nil;
    NSString *q=[NSString stringWithFormat:@"select * from user_reminder where session_id=%@", sessionID];
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    sqlite3_stmt *stmt;
    sqlite3_prepare_v2(appDelegate.database, [q UTF8String], -1, &stmt, NULL);
    if(sqlite3_step(stmt) == SQLITE_ROW){
        char *sessionID=(char *)sqlite3_column_text(stmt, 1);
        int reminderMin=sqlite3_column_int(stmt, 2);
        
        result=[[Reminder alloc]init];
        [result setSessionID:[NSString stringWithUTF8String:sessionID]];
        [result setReminderMinute:[NSNumber numberWithInt:reminderMin]];
    }
    
    if(stmt)sqlite3_finalize(stmt);
    return result;
}

#pragma mark - UserPath method
+(void)saveUserPath:(UserPath *)path{
    NSDateFormatter *formatter=[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss ZZZ"];
    
    NSString *content=[path.pathContent stringByReplacingOccurrencesOfString:@"'" withString:@"''"];
    
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *save=[NSString stringWithFormat:@"insert into user_path(path_id, path_content, create_time, has_image) values('%@','%@','%@', %d)",path.pathID, content, [formatter stringFromDate:[NSDate date]], path.hasImage.intValue];
    
    sqlite3_stmt *stmt;
    
    char *errorMsg;
    if(sqlite3_exec(appDelegate.database, [save UTF8String], nil, &stmt, &errorMsg)!=SQLITE_OK){
        NSLog(@"%@", [NSString stringWithUTF8String:errorMsg]);
    }
}
+(NSMutableArray *)getAllUserPath{
    NSMutableArray *result=[[NSMutableArray alloc]initWithCapacity:0];
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *sql=@"select * from user_path order by id desc";
    
    NSDateFormatter *formatter=[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss ZZZ"];
    
    sqlite3_stmt *stmt;
    sqlite3_prepare_v2(appDelegate.database, [sql UTF8String], -1, &stmt, NULL);
    while(sqlite3_step(stmt) == SQLITE_ROW){
        char *pathID=(char *)sqlite3_column_text(stmt, 1);
        char *pathContent=(char *)sqlite3_column_text(stmt, 2);
        char *createTime=(char *)sqlite3_column_text(stmt, 3);
        int hasImage=sqlite3_column_int(stmt, 4);
        
        UserPath *path=[[UserPath alloc]init];
        
        if(pathID!=nil) [path setPathID:[NSString stringWithUTF8String:pathID]];
        if(pathContent!=nil) [path setPathContent:[NSString stringWithUTF8String:pathContent]];
        if(createTime!=nil) [path setPathCreateTime:[formatter dateFromString:[NSString stringWithUTF8String:createTime]]];
        [path setHasImage:[NSNumber numberWithInt:hasImage]];
        
        [result addObject:path];
    }
    if(stmt) sqlite3_finalize(stmt);
    return result;
}

+(void)deleteUserPath:(UserPath *)path{
    AppDelegate *appDelegate=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    NSString *del=[NSString stringWithFormat:@"delete from user_path where path_id='%@'", path.pathID];
    sqlite3_stmt *stmt;
    sqlite3_exec(appDelegate.database, [del UTF8String], nil, &stmt, nil);
}

@end
