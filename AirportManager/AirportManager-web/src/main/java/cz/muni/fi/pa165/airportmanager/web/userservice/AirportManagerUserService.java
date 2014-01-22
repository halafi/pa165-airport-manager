/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.airportmanager.web.userservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private static final User DEFAULT_USER = getUserInstance("user", "user");
    private static final User DEFAULT_ADMIN = getAdmin();

    private File usersFile = new File(
            new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParentFile().getAbsolutePath() + System.getProperty("file.separator") +"users.us");

    public AirportManagerUserService() {
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
        if(string.equals("admin")){
            return DEFAULT_ADMIN;
        }
        if(string.equals("user")){
            return DEFAULT_USER;
        }
        return getUser(string);
    }

    private void addUser(String username, String password) throws CannotCreateUserException{
        try{
            getUser(username);
            throw new UserAlreadyExistsException("User with name " + username + " already exists.");
        } catch (UsernameNotFoundException ex){
            try(FileWriter wr = new FileWriter(usersFile);
                    BufferedWriter bw = new BufferedWriter(wr)){
                bw.write(username + "=" + password);
                bw.flush();
            } catch (IOException ioex){
                throw new CannotCreateUserException(username, ioex);
            }
        }
    }
    
    private User getUser(String username) throws UsernameNotFoundException{
        try(FileReader input = new FileReader(usersFile);
                BufferedReader bufferedInput = new BufferedReader(input)){
            String line = bufferedInput.readLine();
            String[] split;
            while(line != null){
                split = line.split("[=,]+");
                if(split != null && split.length >= 2){
                    if(split[0].equals(username)){
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
    
    public void createUser(){
        
    }

    private static User getAdmin() {
        Collection<GrantedAuthority> authorities = new ArrayList<>(2);
        authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return new User("admin", "admin", authorities);
    }

    private static User getUserInstance(String name, String password) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        return new User(name, password, authorities);
    }
    
}
