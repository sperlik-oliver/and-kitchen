package tech.sperlikoliver.and_kitchen.Model.Utility

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

open class PropertyChangeAware {

    protected val propertyChangeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener : PropertyChangeListener){
        propertyChangeSupport.addPropertyChangeListener(listener)
    }
    fun removePropertyChangeListener(listener: PropertyChangeListener){
        propertyChangeSupport.removePropertyChangeListener(listener)
    }

}