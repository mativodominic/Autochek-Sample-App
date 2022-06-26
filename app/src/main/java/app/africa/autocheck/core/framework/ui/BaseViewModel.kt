package app.africa.autocheck.core.framework.ui

import androidx.lifecycle.ViewModel
import app.africa.autocheck.core.data.popular.repo.AutoRepositorySource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel: ViewModel(), KoinComponent {
    val repo : AutoRepositorySource by inject()

}