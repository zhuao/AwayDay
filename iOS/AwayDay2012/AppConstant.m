//
//  AppConstant.m
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-8.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "AppConstant.h"

@implementation AppConstant

NSString const *kUserNameKey=@"username";
NSString *kUserPathImageFolder=@"user_path_image";
NSString *kUserJoinListKey=@"user_join_list";
NSString const *kUserWeiboIDKey=@"user_weibo_ID";
NSString const *kUserWeiboTokenKey=@"user_weibo_token_ID";

NSString const *kSessionIDKey=@"session_id";
NSString const *kSessionTitleKey=@"settion_title";
NSString const *kSessionDescriptionKey=@"session_description";
NSString const *kSessionSpeakerKey=@"session_speaker";
NSString const *kSessionStartKey=@"session_start";
NSString const *kSessionEndKey=@"session_end";
NSString const *kSessionLocationKey=@"session_location";

NSString const *kShareImageKey=@"share_image";
NSString const *kDeviceIDKey=@"device_id";
NSString const *kShareTextKey=@"share_text";
NSString const *kTimastampKey=@"timestamp";

NSString const *kPathTextKey=@"text_content";
NSString const *kPathImageKey=@"image_content";

NSString const *kServiceLoadSessionList=@"http://106.120.75.116:8000/sessions_grouped_by_date";
NSString *kServiceUserPath=@"http://awayday2012.herokuapp.com/moment";
NSString *kServiceUserShare=@"http://awayday2012.sinaapp.com/share.php";

@end
