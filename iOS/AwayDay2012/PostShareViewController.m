//
//  PostShareViewController.m
//  AwayDay2012
//
//  Created by xuehai zeng on 12-8-8.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "PostShareViewController.h"
#import "AppDelegate.h"
#import "UserPath.h"
#import "AppHelper.h"
#import "ImageService.h"
#import "ASIFormDataRequest.h"
#import "AppConstant.h"
#import "GTMBase64.h"
#import "SBJsonWriter.h"
#import "WeiboSDK.h"
#import "AFHTTPClient.h"
#import "AFHTTPRequestOperation.h"
#import "AFJSONRequestOperation.h"

#define text_length_limit   140
#define tag_req_post_user_share 1001

@implementation PostShareViewController {
    AppDelegate *appDelegate;
}
@synthesize session = _session;
@synthesize textView = _textView;
@synthesize textCountLabel = _textCountLabel;
@synthesize imageIconView = _imageIconView;
@synthesize userImage = _userImage;
@synthesize sessionTextLabel = _sessionTextLabel;

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.textView becomeFirstResponder];
    if (self.userImage == nil) {
        self.imageIconView.alpha = 0.0f;
    }

    if (self.session == nil) {
        [self.sessionTextLabel setText:@""];
    } else {
        [self.sessionTextLabel setText:[NSString stringWithFormat:@"For %@", self.session.sessionTitle]];
    }


    appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    [appDelegate hideMenuView];

    [self.textCountLabel setText:[NSString stringWithFormat:@"%d/%d", self.textView.text.length, text_length_limit]];
}

#pragma mark - UIAction method
- (IBAction)backButtonPressed:(id)sender {
    self.userImage = nil;
    [self.textView setText:@""];
    [self.navigationController popViewControllerAnimated:YES];

    [appDelegate showMenuView];
}

- (IBAction)sendButtonPressed:(id)sender {
    [AppHelper showInfoView:self.view];
    //to send the share
    NSString *content = self.textView.text;
    if (content.length == 0 && self.userImage == nil) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Input something please" message:@"you need to input something or put a photo" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }

    WBMessageObject *messageToShare = [self messageToShare:content];

    if (messageToShare.imageObject) {
        [self postWeiboWithText:messageToShare.text withImage:self.userImage];
    } else {
        [self postWeiboWithText:messageToShare.text];
    }
}

#pragma mark post methods
- (void)postWeiboWithText:(NSString *)text {
    NSString *accessToken = [appDelegate.userState objectForKey:kUserWeiboTokenKey];
    NSURL *url = [NSURL URLWithString:@"https://api.weibo.com/2/statuses/update.json"];

    NSDictionary *params = @{@"status" : text,
            @"access_token" : accessToken};
    AFHTTPClient *httpClient = [[AFHTTPClient alloc] initWithBaseURL:url];
    [httpClient postPath:nil parameters:params success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *responseStr = [[NSString alloc] initWithData:responseObject encoding:NSUTF8StringEncoding];
        NSLog(@"Request Successful, response '%@'", responseStr);
        [self requestFinished];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"[HTTPClient Error]: %@", error.localizedDescription);
        [self requestFailed];
    }];
}

- (void)postWeiboWithText:(NSString *)text withImage:(UIImage *)image {
    NSString *accessToken = [appDelegate.userState objectForKey:kUserWeiboTokenKey];
    NSURL *url = [NSURL URLWithString:@"https://upload.api.weibo.com/2/statuses/upload.json"];

    NSDictionary *params = @{@"status" : text,
            @"access_token" : accessToken,
            @"pic" : image};

    AFHTTPClient *client = [[AFHTTPClient alloc] initWithBaseURL:url];
    NSURLRequest *urlRequest = [client multipartFormRequestWithMethod:@"POST" path:@"" parameters:params constructingBodyWithBlock:nil];

    AFJSONRequestOperation *requestOperation = [AFJSONRequestOperation JSONRequestOperationWithRequest:urlRequest success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        NSString *responseStr = [[NSString alloc] initWithData:JSON encoding:NSUTF8StringEncoding];
        NSLog(@"Request Successful, response '%@'", responseStr);
        [self requestFinished];
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"Request Failed, response '%@'", JSON);
        NSLog(@"[HTTPClient Error]: %@", error.localizedDescription);
        [self requestFailed];
    }];
    [requestOperation start];
}

- (WBMessageObject *)messageToShare:(NSString *)inputText {
    WBMessageObject *message = [WBMessageObject message];

    message.text = [NSString stringWithFormat:@"#TWAwayDay2013# #%@# %@", self.session.sessionTitle, inputText];

    if (self.userImage) {
        WBImageObject *image = [WBImageObject object];
        image.imageData = UIImagePNGRepresentation(self.userImage);//[NSData dataWithContentsOfFile:self.userImage];
        message.imageObject = image;
    }

    return message;
}

- (IBAction)addImageButtonPressed:(id)sender {
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:nil destructiveButtonTitle:nil otherButtonTitles:nil, nil];
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        [actionSheet addButtonWithTitle:@"Take Photo"];
    }
    [actionSheet addButtonWithTitle:@"Choose from Album"];
    [actionSheet addButtonWithTitle:@"Cancel"];
    [actionSheet setDestructiveButtonIndex:0];
    [actionSheet setCancelButtonIndex:actionSheet.numberOfButtons - 1];
    [actionSheet showInView:self.view];
}

#pragma mark - UIActionSheet delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == actionSheet.numberOfButtons - 1) return;

    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    [picker setDelegate:self];

    if ([[actionSheet buttonTitleAtIndex:buttonIndex] rangeOfString:@"Take"].length > 0) {
        //take photo
        [picker setSourceType:UIImagePickerControllerSourceTypeCamera];
    }
    if ([[actionSheet buttonTitleAtIndex:buttonIndex] rangeOfString:@"Album"].length > 0) {
        //select from album
        [picker setSourceType:UIImagePickerControllerSourceTypeSavedPhotosAlbum];
    }
    [self presentModalViewController:picker animated:YES];
}

#pragma mark - UITextView delegate
- (BOOL)textViewShouldBeginEditing:(UITextView *)textView {
    if (self.textView.text.length < text_length_limit) {
        return YES;
    } else {
        return NO;
    }
}

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    if (range.location >= text_length_limit) {
        return NO;
    } else {
        return YES;
    }
}

- (void)textViewDidChange:(UITextView *)textView {
    if (text_length_limit == -1) return;
    if (self.textView.text.length == text_length_limit) {
        self.textView.text = [self.textView.text substringWithRange:NSMakeRange(0, text_length_limit)];
    }
    [self.textCountLabel setText:[NSString stringWithFormat:@"%d/%d", self.textView.text.length, text_length_limit]];
}

#pragma UIImagePickerViewController delegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    @autoreleasepool {
        self.userImage = [info objectForKey:UIImagePickerControllerOriginalImage];
        float xratio = self.userImage.size.width / 600.0f;
        float yratio = self.userImage.size.height / 600.0f;
        if (xratio > 1.0f || yratio > 1.0f) {
            self.userImage = [ImageService imageByScalingAndCroppingForSize:self.userImage toSize:CGSizeMake(self.userImage.size.width / xratio, self.userImage.size.height / yratio)];
        }
    }
    [self.imageIconView setAlpha:1.0f];
    [picker dismissViewControllerAnimated:YES completion:nil];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [picker dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - util method
- (void)removeInfoView {
    [AppHelper removeInfoView:self.view];
}

- (void)postUserShare2Server {
    NSMutableDictionary *param = [[NSMutableDictionary alloc] initWithCapacity:0];
    if (self.session != nil) {
        [param setObject:self.session.sessionID forKey:kSessionIDKey];
    }
    if (self.userImage != nil) {
        [param setObject:[AppHelper base64EncodeImage:self.userImage] forKey:kShareImageKey];
    }

    NSTimeInterval interval = [[NSDate date] timeIntervalSince1970];
    NSString *timestamp = [NSString stringWithFormat:@"%ld", (long) interval];

    [param setObject:[AppHelper macaddress] forKey:kDeviceIDKey];
    [param setObject:self.textView.text forKey:kShareTextKey];
    [param setObject:[appDelegate.userState objectForKey:kUserNameKey] forKey:kUserNameKey];
    [param setObject:timestamp forKey:kTimastampKey];
    SBJsonWriter *jsonWriter = [[SBJsonWriter alloc] init];
    NSString *paramString = [jsonWriter stringWithObject:param];

    //I'm here
    ASIFormDataRequest *req = [ASIFormDataRequest requestWithURL:[NSURL URLWithString:kServiceUserShare]];
    [req setRequestMethod:@"POST"];
    [req addRequestHeader:@"Content-Type" value:@"application/json"];
    [req appendPostData:[paramString dataUsingEncoding:NSUTF8StringEncoding]];
    [req setTag:tag_req_post_user_share];
    [req setDelegate:self];
    [req startAsynchronous];
}

- (void)postUserPath2Server:(UserPath *)userPath {
    NSMutableDictionary *param = [[NSMutableDictionary alloc] initWithCapacity:0];
    if (self.userImage != nil) {
        //we don't need to submit path's image for now
//        [param setObject:[AppHelper base64DecodeImage:self.userImage] forKey:kShareImageKey];
    }
    [param setObject:[AppHelper macaddress] forKey:kDeviceIDKey];
    [param setObject:userPath.pathContent forKey:kPathTextKey];
    [param setObject:[appDelegate.userState objectForKey:kUserNameKey] forKey:kUserNameKey];
    [param setObject:userPath.pathID forKey:kTimastampKey];
    SBJsonWriter *jsonWriter = [[SBJsonWriter alloc] init];
    NSString *paramString = [jsonWriter stringWithObject:param];

    //I'm here
    ASIFormDataRequest *req = [ASIFormDataRequest requestWithURL:[NSURL URLWithString:kServiceUserPath]];
    [req setRequestMethod:@"POST"];
    [req addRequestHeader:@"Content-Type" value:@"application/json"];
    [req appendPostData:[paramString dataUsingEncoding:NSUTF8StringEncoding]];
    [req addPostValue:paramString forKey:nil];
    [req setTag:tag_req_post_user_share];
    [req setDelegate:self];
    [req startAsynchronous];
}

- (void)requestFinished {
    self.userImage = nil;
    [self.textView setText:@""];
    [AppHelper removeInfoView:self.view];
    [AppHelper showInfoView:self.view withText:@"Share successfully" withLoading:NO];
    [NSTimer scheduledTimerWithTimeInterval:2.0f target:self selector:@selector(removeInfoView) userInfo:nil repeats:NO];

    [appDelegate showMenuView];
    [NSTimer scheduledTimerWithTimeInterval:2.0f target:self.navigationController selector:@selector(popViewControllerAnimated:) userInfo:nil repeats:NO];
}

- (void)requestFailed {
    [AppHelper removeInfoView:self.view];
    [AppHelper showInfoView:self.view withText:@"Share Failed" withLoading:NO];
    [NSTimer scheduledTimerWithTimeInterval:1.0f target:self selector:@selector(removeInfoView) userInfo:nil repeats:NO];
}

- (void)viewDidUnload {
    [super viewDidUnload];
}

@end
