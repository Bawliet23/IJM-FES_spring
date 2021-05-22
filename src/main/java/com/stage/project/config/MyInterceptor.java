package com.stage.project.config;

import com.stage.project.Dao.JournalisationRepository;
import com.stage.project.ProjectApplication;
import com.stage.project.entities.Journalisation;
import com.stage.project.entities.User;
import com.stage.project.services.UserPrincipal;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (!(entity instanceof Journalisation)) {
            UserPrincipal myUserDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = myUserDetails.getUser();
            Date date = new Date();

            Journalisation journalisation = new Journalisation("insert", entity.toString(), user.getFirstName() + " " + user.getLastName(), date);
            journalisation.setNewValue(entity.toString());

            JournalisationRepository myService = SpringContextUtil.getApplicationContext().getBean(JournalisationRepository.class);
            myService.save(journalisation);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (!(entity instanceof Journalisation)) {
            Map<String, Object> oldVal = new HashMap<>();
            Map<String, Object> newVal = new HashMap<>();
            System.out.println(previousState.length+" "+propertyNames.length);
            for (int i = 0; i < previousState.length; i++) {
                if (propertyNames[i]!=null && previousState[i]!=null && !propertyNames[i].equals("lastModifiedDate") && !propertyNames[i].equals("lastModifiedBy")
                        && !propertyNames[i].equals("createdDate") && !propertyNames[i].equals("createdBy"))
                    oldVal.put(propertyNames[i], previousState[i].toString());
            }
            for (int i = 0; i < currentState.length; i++) {
                if ( propertyNames[i]!=null && currentState[i]!=null && !propertyNames[i].equals("lastModifiedDate") && !propertyNames[i].equals("lastModifiedBy")
                        && !propertyNames[i].equals("createdDate") && !propertyNames[i].equals("createdBy"))
                    newVal.put(propertyNames[i], currentState[i].toString());
            }

            UserPrincipal myUserDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = myUserDetails.getUser();
            Date date = new Date();

            Journalisation journalisation = new Journalisation("update", entity.toString(), user.getFirstName() + " " + user.getLastName(), date);
            journalisation.setOldvalue(oldVal.toString());
            journalisation.setNewValue(newVal.toString());

            JournalisationRepository myService = SpringContextUtil.getApplicationContext().getBean(JournalisationRepository.class);
            myService.save(journalisation);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (!(entity instanceof Journalisation)) {
            UserPrincipal myUserDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = myUserDetails.getUser();
            Date date = new Date();

            Journalisation journalisation = new Journalisation("delete", entity.toString(), user.getFirstName() + " " + user.getLastName(), date);

            JournalisationRepository myService = SpringContextUtil.getApplicationContext().getBean(JournalisationRepository.class);
            myService.save(journalisation);
        }
        super.onDelete(entity, id, state, propertyNames, types);
    }
}
