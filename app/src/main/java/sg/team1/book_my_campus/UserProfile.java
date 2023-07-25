package sg.team1.book_my_campus;

import android.net.Uri;

public class UserProfile {

    private static String name;
    private static String email;
    private static String password;
    private static String userId;
    private static String mobile;
    private static String faSwitch;
    private static Uri profilePic;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserProfile.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserProfile.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserProfile.password = password;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserProfile.userId = userId;
    }

    public static String getMobile() {
        return mobile;
    }

    public static void setMobile(String mobile) {
        UserProfile.mobile = mobile;
    }

    public static String getFaSwitch() {
        return faSwitch;
    }

    public static void setFaSwitch(String faSwitch) {
        UserProfile.faSwitch = faSwitch;
    }

    public static Uri getProfilePic() {
        return profilePic;
    }

    public static void setProfilePic(Uri profilePic) {
        UserProfile.profilePic = profilePic;
    }
}
