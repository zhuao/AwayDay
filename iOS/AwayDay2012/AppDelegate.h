//
//  AppDelegate.h
//  AwayDay2012
//
//  Created by xuehai zeng on 7/31/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import "AgendaViewController.h"
#import "MenuViewController.h"
#import "SettingViewController.h"
#import "UserPathViewController.h"
#import "WeiboSDK.h"
#import <sqlite3.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, WeiboSDKDelegate>

@property (strong, nonatomic) UIWindow *window;
@property(nonatomic, strong) UINavigationController *navigationController;
@property(nonatomic, strong) AgendaViewController *agendaViewController;
@property(nonatomic, strong) SettingViewController *settingViewController;
@property(nonatomic, strong) MenuViewController *menuViewController;
@property(nonatomic, strong) UserPathViewController *userPathViewController;
@property(nonatomic, strong) NSMutableDictionary *userState;
@property(nonatomic, readonly) sqlite3 *database;

/**
 save user's state to the NSUserDefault
 */
-(void)saveUserState;

/*
 hide the bottom menu view
 */
-(void)hideMenuView;

/**
 show the bottom menu view
 */
-(void)showMenuView;

- (NSString *) getDBPath;
- (void) copyDatabaseIfNeeded;
-(void)openDatabase;

@end
