package com.rickandmortycharacters.di

import com.rickandmortycharacters.data.CharactersRepository
import com.rickandmortycharacters.data.CharactersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Repository {

    @Binds
    @Singleton
    abstract fun provideRepository(impl: CharactersRepositoryImpl): CharactersRepository
}
