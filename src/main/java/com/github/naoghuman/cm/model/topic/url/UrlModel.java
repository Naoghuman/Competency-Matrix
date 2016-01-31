/*
 * Copyright (C) 2016 PRo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.cm.model.topic.url;

import com.github.naoghuman.cm.configuration.api.IEntityConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IEntityConfiguration.ENTITY__TABLE_NAME__URL_MODEL)
@NamedQueries({
    @NamedQuery(
            name = IEntityConfiguration.NAMED_QUERY__NAME__URL_FIND_BY_ID,
            query = IEntityConfiguration.NAMED_QUERY__QUERY__URL_FIND_BY_ID),
    @NamedQuery(
            name = IEntityConfiguration.NAMED_QUERY__NAME__URL_FIND_BY_PARENT,
            query = IEntityConfiguration.NAMED_QUERY__QUERY__URL_FIND_BY_PARENT)
})
public class UrlModel implements Comparable<UrlModel>, Externalizable, IEntityConfiguration {

    private static final long serialVersionUID = 1L;
    
    public UrlModel() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID__URL_MODEL;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_NAME__ID)
    public long getId() {
        if (this.idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (this.idProperty == null) {
            _id = id;
        } else {
            this.idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  TOPIC-ID ---------------------------------------------------------
    private LongProperty topicIdProperty;
    private long _topicId = DEFAULT_ID__TOPIC_MODEL;

    @Column(name = COLUMN_NAME__TOPIC_ID)
    public long getTopicId() {
        if (this.topicIdProperty == null) {
            return _topicId;
        } else {
            return topicIdProperty.get();
        }
    }

    public final void setTopicId(long topicId) {
        if (this.topicIdProperty == null) {
            _topicId = topicId;
        } else {
            this.topicIdProperty.set(topicId);
        }
    }

    public LongProperty topicIdProperty() {
        if (topicIdProperty == null) {
            topicIdProperty = new SimpleLongProperty(this, COLUMN_NAME__TOPIC_ID, _topicId);
        }
        return topicIdProperty;
    }
    // END  TOPIC-ID -----------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = COLUMN_NAME__GENERATION_TIME)
    public long getGenerationTime() {
        if (this.generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.get();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (this.generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            this.generationTimeProperty.set(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this, COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------

    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = COLUMN_NAME__TEXT)
    public String getText() {
        if (this.textProperty == null) {
            return _text;
        } else {
            return textProperty.get();
        }
    }
    
    public void setText(String text) {
        if (this.textProperty == null) {
            _text = text;
        } else {
            this.textProperty.set(text);
        }
    }
    
    public StringProperty textProperty() {
        if (textProperty == null) {
            textProperty = new SimpleStringProperty(this, COLUMN_NAME__TEXT, _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------
    
    // START  URL --------------------------------------------------------------
    private StringProperty urlProperty = null;
    private String _url = SIGN__EMPTY;
    
    @Column(name = COLUMN_NAME__URL)
    public String getUrl() {
        if (this.urlProperty == null) {
            return _url;
        } else {
            return urlProperty.get();
        }
    }
    
    public void setUrl(String url) {
        if (this.urlProperty == null) {
            _url = url;
        } else {
            this.urlProperty.set(url);
        }
    }
    
    public StringProperty urlProperty() {
        if (urlProperty == null) {
            urlProperty = new SimpleStringProperty(this, COLUMN_NAME__URL, _url);
        }
        return urlProperty;
    }
    // END  URL ----------------------------------------------------------------

    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = COLUMN_NAME__DESCRIPTION)
    public String getDescription() {
        if (this.descriptionProperty == null) {
            return _description;
        } else {
            return descriptionProperty.get();
        }
    }
    
    public void setDescription(String description) {
        if (this.descriptionProperty == null) {
            _description = description;
        } else {
            this.descriptionProperty.set(description);
        }
    }
    
    public StringProperty descriptionProperty() {
        if (descriptionProperty == null) {
            descriptionProperty = new SimpleStringProperty(this, COLUMN_NAME__DESCRIPTION, _description);
        }
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getTopicId())
                .append(this.getGenerationTime())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this) {
            return false;
        }
        
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        
        final UrlModel other = (UrlModel) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getTopicId(), other.getTopicId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int compareTo(UrlModel other) {
        return new CompareToBuilder()
                .append(this.getText(), other.getText())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .append(this.getTopicId(), other.getTopicId())
                .append(this.getId(), other.getId())
                .toComparison();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(COLUMN_NAME__ID, this.getId())
                .append(COLUMN_NAME__TOPIC_ID, this.getTopicId())
                .append(COLUMN_NAME__TEXT, this.getText())
                .append(COLUMN_NAME__URL, this.getUrl())
                .append(COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getTopicId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(StringEscapeUtils.escapeHtml4(this.getText()));
        out.writeObject(StringEscapeUtils.escapeHtml4(this.getUrl()));
        out.writeObject(StringEscapeUtils.escapeHtml4(this.getDescription()));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setTopicId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setText(StringEscapeUtils.unescapeHtml4(String.valueOf(in.readObject())));
        this.setUrl(StringEscapeUtils.unescapeHtml4(String.valueOf(in.readObject())));
        this.setDescription(StringEscapeUtils.unescapeHtml4(String.valueOf(in.readObject())));
    }
    
}

