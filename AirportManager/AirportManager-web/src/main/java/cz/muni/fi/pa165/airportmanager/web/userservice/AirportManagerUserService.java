
package cz.muni.fi.pa165.airportmanager.web.userservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Chorke
 */
public class AirportManagerUserService implements UserDetailsService{
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final UserDetails DEFAULT_USER = getUserInstance("user", "user");
    private static final UserDetails DEFAULT_ADMIN = getAdmin();
    private static final UserDetails DEFAULT_REST = getRESTClient();
    
    private File usersFile = new File(
            new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParentFile().getAbsolutePath() + System.getProperty("file.separator") +"users.us");; 

    public AirportManagerUserService() throws IOException {
        if(!usersFile.canWrite()) {
            usersFile = new File("users.us");
        }
        if(!usersFile.exists()){
            try{
                usersFile.createNewFile();
            } catch (IOException ex){
                System.out.println(ex);
            }
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        if(string == null || string.isEmpty()){
            throw new UsernameNotFoundException("User not found {username = " + string + "}");
        }
        if(!validUsername(string)){
            throw new UsernameNotFoundException("Unsupported character: " + string);
        }
        if(string.equalsIgnoreCase("admin")){
            return DEFAULT_ADMIN;
        }
        if(string.equalsIgnoreCase("user")){
            return DEFAULT_USER;
        }
        return getUser(string);
    }

    /**
     * Creates new user with given {@code username} and {@code password}.
     * @param username
     * @param password
     * @throws CannotCreateUserException If {@code username} is not alphanumeric
     *      or username already exists.
     */
    public void addUser(String username, String password) throws CannotCreateUserException{
        if(!validUsername(username)){
            throw new InvalidUsernameException("Unsupported character: " + username);
        }
        try{
            loadUserByUsername(username);
            throw new UserAlreadyExistsException("User with name " + username + " already exists.");
        } catch (UsernameNotFoundException ex){
            try(FileWriter wr = new FileWriter(usersFile, true);
                    BufferedWriter bw = new BufferedWriter(wr)){
                bw.write(username + "/" + escape(password));
                bw.newLine();
                bw.flush();
            } catch (IOException ioex){
                throw new CannotCreateUserException(username, ioex);
            }
        }
    }
    
    private UserDetails getUser(String username) throws UsernameNotFoundException{
        try(FileReader input = new FileReader(usersFile);
                BufferedReader bufferedInput = new BufferedReader(input)){
            String line = bufferedInput.readLine();
            String[] split;
            while(line != null){
                split = splitLine(line);
                if(split.length >= 2){
                    if(split[0].equalsIgnoreCase(username)){
                        return getUserInstance(split[0], split[1]);
                    }
                }
                line = bufferedInput.readLine();
            }
        } catch (IOException ex){
            throw new UsernameNotFoundException("User not found {username = " + username + "}", ex);
        }
        throw new UsernameNotFoundException("User not found {username = " + username + "}");
    }

    private static UserDetails getAdmin() {
        Collection<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return new User("admin", "admin", authorities);
    }
    
    private static UserDetails getRESTClient() {
        Collection<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return new User("rest", "rest", authorities);
    }

    private static UserDetails getUserInstance(String name, String password) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return new User(name, password, authorities);
    }

    private boolean validUsername(String username) {
        return username.matches("[a-zA-Z0-9]+");
    }

    private String escape(String string) {
        return string.replaceAll("/", "//");
    }

    private String[] splitLine(String line) {
        ArrayList<String> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        String sub;
        for(int i = 0; i < line.length(); i++){
            sub = line.substring(i, i+1);
            if(sub.equals("/")){
                if(i < line.length()-1){
                    if(line.substring(i+1, i+2).equals("/")){
                        buffer.append("/");
                        i++;
                    } else {
                       list.add(buffer.toString());
                       buffer = new StringBuffer();
                    }
                }
            } else {
                buffer.append(sub);
            }
        }
        list.add(buffer.toString());
        return list.toArray(new String[list.size()]);
    }
}
