package com.me.themoviedb.domain.model

import com.me.themoviedb.testutil.fake.FakeDataGenerator
import com.me.themoviedb.testutil.fake.FakeDataGenerator.FakeMemberType.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class MovieCreditsTest{

    @Test
    fun getDirectorNameList() {
        val cast = FakeDataGenerator.createMembersList(1, 5, CAST)
        val crew = FakeDataGenerator.createMembersList(6, 10, CREW)
        val director = FakeDataGenerator.createMembersList(11, 15, DIRECTOR)

        val credits = MovieCredits(cast + crew + director)

        val names = director.map { it.name }

        assertThat(credits.directors, equalTo(names))
    }

    @Test
    fun getDisplayDirectorNameList() {
        val director = FakeDataGenerator.createMember(1, DIRECTOR)
        val directorList2 = FakeDataGenerator.createMembersList(1, 2, DIRECTOR)
        val directorList5 = FakeDataGenerator.createMembersList(1, 5, DIRECTOR)

        assertThat(MovieCredits(listOf(director)).displayDirectorNames, equalTo(director.name))

        assertThat(MovieCredits(directorList2).displayDirectorNames, equalTo("${directorList2[0].name} & ${directorList2[1].name}"))

        assertThat(MovieCredits(directorList5).displayDirectorNames, equalTo("${directorList2[0].name} and 4 more"))
    }
}