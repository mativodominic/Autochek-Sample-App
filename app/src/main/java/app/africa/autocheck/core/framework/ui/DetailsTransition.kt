package app.africa.autocheck.core.framework.ui

import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

class DetailsTransition : TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER;
        addTransition(ChangeBounds()).
        addTransition( ChangeTransform()).
        addTransition( ChangeImageTransform())
    }
}