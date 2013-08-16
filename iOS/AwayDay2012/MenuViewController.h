//
//  MenuViewController.h
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.

//  the bottom menu view

#import <UIKit/UIKit.h>

@interface MenuViewController : UIViewController

@property(nonatomic, strong) IBOutlet UIImageView *tapImageView;
@property(nonatomic, strong) IBOutlet UIImageView *chooseFlagImageView;
@property(nonatomic, strong) IBOutlet UIButton *agendaViewButton;
@property(nonatomic, strong) IBOutlet UIButton *pathViewButton;
@property(nonatomic, strong) IBOutlet UIButton *settingViewButton;

-(IBAction)agendaButtonPressed:(id)sender;
-(IBAction)settingButtonPressed:(id)sender;
-(IBAction)myPathButtonPressed:(id)sender;

-(IBAction)handleTapGesture:(UITapGestureRecognizer *)sender;
-(IBAction)handleSwipeGesture:(UISwipeGestureRecognizer *)sender;

@end
