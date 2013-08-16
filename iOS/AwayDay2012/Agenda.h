//
//  Agenda.h
//  AwayDay2012
//
//  Created by xuehai zeng on 7/31/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Agenda : NSObject

@property(nonatomic, strong) NSDate *agendaDate;
@property(nonatomic, strong) NSMutableArray *sessions;

+(Agenda *)createAgenda:(NSDictionary *) agendaProperties;

@end
