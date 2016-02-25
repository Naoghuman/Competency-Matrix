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
package com.github.naoghuman.cm.model.notes;

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
@Table(name = IEntityConfiguration.ENTITY__TABLE_NAME__NOTES_MODEL)
@NamedQueries({
    @NamedQuery(
            name = IEntityConfiguration.NAMED_QUERY__NAME__NOTES_FIND_BY_ID,
            query = IEntityConfiguration.NAMED_QUERY__QUERY__NOTES_FIND_BY_ID)
})
public class NotesModel implements Comparable<NotesModel>, Externalizable, IEntityConfiguration {

    private static final long serialVersionUID = 1L;
    
    public NotesModel() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID__NOTES_MODEL;

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
    
    // START  MATRIX-ID --------------------------------------------------------
    private LongProperty matrixIdProperty;
    private long _matrixId = DEFAULT_ID__MATRIX_MODEL;

    @Column(name = COLUMN_NAME__MATRIX_ID)
    public long getMatrixId() {
        if (this.matrixIdProperty == null) {
            return _matrixId;
        } else {
            return matrixIdProperty.get();
        }
    }

    public final void setMatrixId(long matrixId) {
        if (this.matrixIdProperty == null) {
            _matrixId = matrixId;
        } else {
            this.matrixIdProperty.set(matrixId);
        }
    }

    public LongProperty matrixIdProperty() {
        if (matrixIdProperty == null) {
            matrixIdProperty = new SimpleLongProperty(this, COLUMN_NAME__MATRIX_ID, _matrixId);
        }
        return matrixIdProperty;
    }
    // END  MATRIX-ID ----------------------------------------------------------
    
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
    
    // START  NOTES ------------------------------------------------------------
    private StringProperty notesProperty = null;
    private String _notes = SIGN__EMPTY;
    
    @Column(name = COLUMN_NAME__NOTES)
    public String getNotes() {
        if (this.notesProperty == null) {
            return _notes;
        } else {
            return notesProperty.get();
        }
    }
    
    public void setNotes(String notes) {
        if (this.notesProperty == null) {
            _notes = notes;
        } else {
            this.notesProperty.set(notes);
        }
    }
    
    public StringProperty notesProperty() {
        if (notesProperty == null) {
            notesProperty = new SimpleStringProperty(this, COLUMN_NAME__NOTES, _notes);
        }
        return notesProperty;
    }
    // END  NOTES --------------------------------------------------------------

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getMatrixId())
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
        
        final NotesModel other = (NotesModel) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getMatrixId(), other.getMatrixId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int compareTo(NotesModel other) {
        return new CompareToBuilder()
                .append(this.getId(), other.getId())
                .append(this.getMatrixId(), other.getMatrixId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .toComparison();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(COLUMN_NAME__ID, this.getId())
                .append(COLUMN_NAME__MATRIX_ID, this.getMatrixId())
                .append(COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getMatrixId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(StringEscapeUtils.escapeHtml4(this.getNotes()));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setMatrixId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setNotes(StringEscapeUtils.unescapeHtml4(String.valueOf(in.readObject())));
    }
    
}
