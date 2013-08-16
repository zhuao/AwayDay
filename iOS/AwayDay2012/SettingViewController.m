//
//  SettingViewController.m
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "SettingViewController.h"
#import "AppDelegate.h"
#import "AppConstant.h"
#import "AppHelper.h"

@interface SettingViewController ()

@end

@implementation SettingViewController

@synthesize userNameField = _userNameField;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self setTitle:@"Settings"];
}

- (void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    NSString *username = [delegate.userState objectForKey:kUserNameKey];
    [self.userNameField setText:username];
    [self.userNameField becomeFirstResponder];
}

#pragma mark - util method
-(void)removeInfoView{
    [AppHelper removeInfoView:self.view];
}

#pragma mark - UIAction method
-(IBAction)saveButtonPressed:(id)sender{
    NSString *inputUserName = self.userNameField.text;
    if(inputUserName.length==0){
        UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"Name is blank" message:@"Please input your name" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    [self.userNameField resignFirstResponder];
    AppDelegate *delegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [delegate.userState setObject:inputUserName forKey:kUserNameKey];
    [delegate saveUserState];
    
    if ([inputUserName isEqualToString:@"r0ys1ngh4m"]) {//extract
    //enable edit/delete session feature.
    }
    
    [AppHelper showInfoView:self.view withText:@"Saved!" withLoading:NO];
    [NSTimer scheduledTimerWithTimeInterval:1.0f target:self selector:@selector(removeInfoView) userInfo:nil repeats:NO];
}
-(IBAction)handleTap:(UITapGestureRecognizer *)sender{
    [self.userNameField resignFirstResponder];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

@end
