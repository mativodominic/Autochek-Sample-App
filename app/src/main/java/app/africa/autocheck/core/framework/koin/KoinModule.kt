package app.africa.autocheck.core.framework.koin

import org.koin.dsl.module

val rootModule = module {
    //single { AppDatabase.getInstance(androidContext()) }
}

val repositoryModule = module {
    //single<UserRepositorySource> { UserRepository() }
}

val remoteModule = module {
    //single<OauthTokenRemoteSource> { OauthTokenRemote() }
}

val localModule = module {
    //single<UserTypeLocalSource> { UserTypeLocal() }
}

