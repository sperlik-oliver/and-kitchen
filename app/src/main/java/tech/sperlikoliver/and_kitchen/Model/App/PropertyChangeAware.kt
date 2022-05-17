package tech.sperlikoliver.and_kitchen.Model.App

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

interface PropertyChangeAware {


    val propertyChangeSupport : PropertyChangeSupport

    fun addPropertyChangeListener(listener : PropertyChangeListener){
        propertyChangeSupport.addPropertyChangeListener(listener)
    }
    fun removePropertyChangeListener(listener: PropertyChangeListener){
        propertyChangeSupport.removePropertyChangeListener(listener)
    }

}


