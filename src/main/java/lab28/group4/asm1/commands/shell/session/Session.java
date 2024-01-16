package lab28.group4.asm1.commands.shell.session;

public class Session {
    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setSession(String username, String password) {
        Session.username = username;
        Session.password = password;
    }

    public static void clearSession() {
        Session.username = null;
        Session.password = null;
    }
}
