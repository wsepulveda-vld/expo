#import <Foundation/Foundation.h>
#import <UserNotifications/UserNotifications.h>
#import <EXCore/EXInternalModule.h>
#import <EXPermissionsInterface.h>

NS_ASSUME_NONNULL_BEGIN

@interface EXUserNotificationCenterProxyInVanillaApps : NSObject <EXInternalModule, EXUserNotificationCenterProxyInterface>
@end

NS_ASSUME_NONNULL_END
