package com.ujujzk.ee.presentation.navigation

interface BackButtonListener {
    /**
     * @return  true if Back press is handled, false otherwise
     */
    fun onBackPressed(): Boolean
}