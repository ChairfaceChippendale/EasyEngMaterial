package com.ujujzk.ee.catalog.di

import android.os.Bundle
import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ujujzk.ee.catalog.CatalogFragment
import com.ujujzk.ee.catalog.CatalogFragment.Companion.EXTRA_FLOW_QUALIFIER
import com.ujujzk.ee.catalog.CatalogViewModel
import com.ujujzk.ee.presentation.di.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val catalogModule = module {

    viewModel { (params: Bundle?) ->
        val flowQualifier = params?.getSerializable(EXTRA_FLOW_QUALIFIER) as? FlowQualifier
            ?: throw IllegalArgumentException("Property $EXTRA_FLOW_QUALIFIER could not be read")

        CatalogViewModel(
            logger = get(named(LOG_UI)),
            router = when (flowQualifier){
                FlowQualifier.DICTIONARY -> get(named(KOIN_NAV_DIC_ROUTER))
                FlowQualifier.VOCABULARY -> get(named(KOIN_NAV_VOC_ROUTER))
            },
            observeDictionariesUc = get(),
            test = get(),
            getPackUseCase = get()
        )
    }

    factory(named(SCREEN_CATALOG)) { (flowQualifier: FlowQualifier) ->
        FragmentScreen(SCREEN_CATALOG) {
            CatalogFragment().apply {
                arguments = bundleOf(
                    EXTRA_FLOW_QUALIFIER to flowQualifier
                )
            }
        }
    }
}