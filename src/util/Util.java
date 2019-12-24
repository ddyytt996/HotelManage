package util;

import domain.manageUser.Menu;
import domain.manageUser.Role;

import java.util.Collection;
import java.util.HashSet;

public class Util {
    public static Collection createRoles(HashSet allFalseRoles, HashSet rightRoles){
        HashSet<Integer> check = new HashSet<Integer>();
        for(Object object:rightRoles){

            Role role = (Role) object;
            check.add(role.getId());
        }
        for(Object object:allFalseRoles){
            Role role = (Role)object;
            if(check.add(role.getId())){
                continue;
            }else {
                role.setChecked(true);
            }
        }
        return allFalseRoles;
    }
    public static Collection createMenus(HashSet allFalseMenus, HashSet rightMenus){
        HashSet<Integer> check = new HashSet<Integer>();
        for(Object object:rightMenus){
            Menu menu = (Menu)object;
            check.add(menu.getId());
        }
        for(Object object:allFalseMenus){
            Menu menu = (Menu)object;
            if(check.add(menu.getId())){
                continue;
            }else {
                menu.setChecked(true);
            }
        }
        return allFalseMenus;
    }
}
