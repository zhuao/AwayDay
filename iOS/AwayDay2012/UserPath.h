//
//  UserPath.h
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-9.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserPath : NSObject

@property(nonatomic, strong) NSString *pathID;
@property(nonatomic, strong) NSString *pathContent;
@property(nonatomic, strong) NSDate *pathCreateTime;
@property(nonatomic, strong) UIImage *pathImage;
@property(nonatomic, strong) NSNumber *hasImage;

-(void)save;
-(void)drop;
+(NSMutableArray *)getAllUserPath;
-(UIImage *)loadLocalPathImage;

@end
