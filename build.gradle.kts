buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

plugins {
    //https://github.com/jraska/modules-graph-assert
    //https://dreampuf.github.io/GraphvizOnline
    //> gradle assertModuleGraph
    //> gradle generateModulesGraphvizText -Pmodules.graph.output.gv=all_modules
/*
      digraph G {
         graph [ranksep=5];
         ...
      }
*/
    id("com.jraska.module.graph.assertion").version("1.1.0")
}

moduleGraphAssert {
    //https://github.com/jraska/modules-graph-assert
    moduleLayers = arrayOf(":app", ":flow:\\S*", ":presentation", ":domain")
    moduleLayers = arrayOf(":app", ":flow:\\S*", ":widget:\\\\S*", ":presentation", ":domain")
    moduleLayers = arrayOf(":app", ":source:\\S*", ":data", ":domain")
    restricted = arrayOf(":presentation -X> :data")
    restricted = arrayOf(":data -X> :presentation")
    restricted = arrayOf(":flow:\\S* -X> :data")
    restricted = arrayOf(":source:\\S* -X> :presentation")
    restricted = arrayOf(":widget:\\S* -X> :flow:\\S*")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

