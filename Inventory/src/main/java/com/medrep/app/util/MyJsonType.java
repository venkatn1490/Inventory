package com.medrep.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medrep.app.model.ChatMessages;

public class MyJsonType implements UserType {

@Override
public int[] sqlTypes() {
    return new int[] { Types.VARCHAR };
}

@Override
public Class<ChatMessages> returnedClass() {
    return ChatMessages.class;
}

@Override
public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner)
        throws HibernateException, SQLException {
    final String cellContent = rs.getString(names[0]);
    if (cellContent == null) {
        return null;
    }
    try {
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(cellContent.getBytes("UTF-8"), returnedClass());
    } catch (final Exception ex) {
        throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
    }
}

@Override
public void nullSafeSet(final PreparedStatement ps, final Object value, final int idx, final SessionImplementor session)
        throws HibernateException, SQLException {
    if (value == null) {
        ps.setNull(idx, Types.VARCHAR);
        return;
    }
    try {
        final ObjectMapper mapper = new ObjectMapper();
        final StringWriter w = new StringWriter();
        mapper.writeValue(w, value);
        w.flush();
        ps.setObject(idx, w.toString(), Types.VARCHAR);
    } catch (final Exception ex) {
        throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
    }
}

@Override
public Object deepCopy(final Object value) throws HibernateException {
    try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(value);
        oos.flush();
        oos.close();
        bos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
        return new ObjectInputStream(bais).readObject();
    } catch (Exception ex) {
        throw new HibernateException(ex);
    }
}

@Override
public boolean isMutable() {
    return true;
}

@Override
public Serializable disassemble(final Object value) throws HibernateException {
    return (Serializable) this.deepCopy(value);
}

@Override
public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
    return this.deepCopy(cached);
}

@Override
public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
    return this.deepCopy(original);
}

@Override
public boolean equals(Object x, Object y) throws HibernateException {
    return Objects.equals(x, y);
}

@Override
public int hashCode(Object x) throws HibernateException {
    return Objects.hashCode(x);
}

}