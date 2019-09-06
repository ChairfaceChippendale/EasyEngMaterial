package com.ujujzk.ee.ui.navigation

interface BackButtonListener {
    /**
     * @return  true if Back press is handled, false otherwise
     */
    fun onBackPressed(): Boolean
}