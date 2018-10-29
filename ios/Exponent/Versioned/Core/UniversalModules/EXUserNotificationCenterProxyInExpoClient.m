//  Copyright © 2018-present 650 Industries. All rights reserved.

#import "EXUserNotificationCenterProxyInExpoClient.h"
#import "EXUserNotificationCenter.h"

@implementation EXUserNotificationCenterProxyInExpoClient

+ (const NSArray<Protocol *> *)exportedInterfaces
{
  return @[@protocol(EXUserNotificationCenterProxyInterface)];
}

+ (instancetype)sharedInstance {
  static EXUserNotificationCenterProxyInExpoClient * instance;
  static dispatch_once_t once;
  dispatch_once(&once, ^{
    if (!instance) {
      instance = [[EXUserNotificationCenterProxyInExpoClient alloc] init];
    }
  });
  return instance;
}

- (void)getNotificationSettingsWithCompletionHandler:(void(^)(UNNotificationSettings *settings))completionHandler
{
  [[EXUserNotificationCenter sharedInstance] getNotificationSettingsWithCompletionHandler:completionHandler];
}

- (void)requestAuthorizationWithOptions:(UNAuthorizationOptions)options completionHandler:(void (^)(BOOL granted, NSError *__nullable error))completionHandler
{
  [[EXUserNotificationCenter sharedInstance] requestAuthorizationWithOptions:options completionHandler:completionHandler];
}

@end
