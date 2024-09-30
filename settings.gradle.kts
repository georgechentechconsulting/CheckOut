pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                username =
                    "AavLAwvR0bgEjN0Vnntme6wTkrcJ5WqTE6bGfU8wIIIOk2we7GemWaavTlyQ_7sOa3Xx0TvwrT5fOIx0"
                password =
                    "EJJtJlNmwIYZfuZKVjTU32lCapUDwwk0JbaR83S1gn109pkDhlFyJQqyrb044JxLGuydlO0AcfntFz0f"
            }
        }
    }

    rootProject.name = "PayPal"
    include(":app")
}
