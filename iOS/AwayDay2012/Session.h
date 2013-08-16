//
//  Session.h
//  AwayDay2012
//
//  Created by xuehai zeng on 7/31/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SessionAddress.h"

@interface Session : NSObject

@property(nonatomic, strong) NSString *sessionID;
@property(nonatomic, strong) NSString *sessionTitle;
@property(nonatomic, strong) NSString *sessionNote;
@property(nonatomic, strong) NSString *sessionSpeaker;
@property(nonatomic, strong) NSDate *sessionStartTime;
@property(nonatomic, strong) NSDate *sessionEndTime;
@property(nonatomic, strong) NSString *sessionAddress;

-(Session *)createSession:(NSDictionary *)sessionProperies;

@end
