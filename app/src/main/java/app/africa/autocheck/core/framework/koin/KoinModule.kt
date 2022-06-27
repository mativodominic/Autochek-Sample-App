package app.africa.autocheck.core.framework.koin

import app.africa.autocheck.core.data.cars.remote.CarsRemote
import app.africa.autocheck.core.data.cars.remote.CarsRemoteSource
import app.africa.autocheck.core.data.popular.remote.PopularMakesRemote
import app.africa.autocheck.core.data.popular.remote.PopularMakesRemoteSource
import app.africa.autocheck.core.data.popular.repo.AutoRepository
import app.africa.autocheck.core.data.popular.repo.AutoRepositorySource
import org.koin.dsl.module

/**
 * @author Dominic Mativo
 */
val rootModule = module {
    //single { AppDatabase.getInstance(androidContext()) }
}

val repositoryModule = module {
    single<AutoRepositorySource> { AutoRepository() }
}

val remoteModule = module {
    single<CarsRemoteSource> {CarsRemote()}
    single<PopularMakesRemoteSource> {PopularMakesRemote()}
}

val localModule = module {
    //single<UserTypeLocalSource> { UserTypeLocal() }
}

