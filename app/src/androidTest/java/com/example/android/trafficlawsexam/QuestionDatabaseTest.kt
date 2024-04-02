/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trafficlawsexam

import androidx.lifecycle.map
import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trafficlawsexam.database.Question
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class QuestionDatabaseTest {

    private lateinit var questionDao: QuestionDatabaseDao
    private lateinit var db: QuestionDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = inMemoryDatabaseBuilder(context, QuestionDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        questionDao = db.getQuestionDatabaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllQuestions() {
        val question = Question()
        questionDao.insert(question)
        val lastQuestion = questionDao.getLastQuestion()
        assertEquals(lastQuestion?.lastGivenAnswerNumber, -1)
//        val questionsCount = questionDao.getAllQuestions().value?.count()
//        val questionsCount = questionDao.getAllQuestions().map { questions ->
//            questions.count()
//        }
        val questionsCount = questionDao.getAllQuestions().count()
        assertEquals(questionsCount, 1)
    }
}

