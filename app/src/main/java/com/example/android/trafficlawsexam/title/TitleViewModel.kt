package com.example.android.trafficlawsexam.title

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.android.trafficlawsexam.database.Question
import com.example.android.trafficlawsexam.database.QuestionDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TitleViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private lateinit var questions : MutableList<Question>

    init {
        uiScope.launch {
            questions = getAllQuestions()
            Log.i("Test", "Questions count: ${questions.count()}")
            if (questions.count() == 0) {
                initializeQuestionDatabase()
                questions = getAllQuestions()
                Log.i("Test", "Questions count: ${questions.count()}")
            }
        }
    }

    private suspend fun getAllQuestions(): MutableList<Question> {
        return withContext(Dispatchers.IO) {
            dao.getAllQuestions()
        }
    }

    private suspend fun insert(question: Question) {
        withContext(Dispatchers.IO) {
            dao.insert(question)
        }
    }

    private suspend fun initializeQuestionDatabase(){
        // pdd_01_01
        var newQuestion = Question(
            text = "В каком случае водитель совершит вынужденную остановку?",
            firstAnswer = "1. Остановившись непосредственно перед пешеходным переходом, чтобы уступить дорогу пешеходу.",
            secondAnswer = "2. Остановившись на проезжей части из-за технической неисправности транспортного средства.",
            thirdAnswer = "3. В обоих перечисленных случаях.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_01_02
        newQuestion = Question(
            text = "Разрешен ли Вам поворот на дорогу с грунтовым покрытием?",
            firstAnswer = "1. Разрешен.",
            secondAnswer = "2. Разрешен только при технической неисправности транспортного средства.",
            thirdAnswer = "3. Запрещен.",
            correctAnswerNumber = 1,
            imagePath = "pdd_01_02")
        insert(newQuestion)

        // pdd_01_03
        newQuestion = Question(
            text = "Можно ли Вам остановиться в указанном месте для посадки пассажира?",
            firstAnswer = "1. Можно.",
            secondAnswer = "2. Можно, если Вы управляете такси.",
            thirdAnswer = "3. Нельзя.",
            correctAnswerNumber = 1,
            imagePath = "pdd_01_03")
        insert(newQuestion)

        // pdd_01_04
        newQuestion = Question(
            text = "Какие из указанных знаков запрещают движение водителям мопедов?",
            firstAnswer = "1. Только А.",
            secondAnswer = "2. Только Б.",
            thirdAnswer = "3. В и Г.",
            fourthAnswer = "4. Все.",
            correctAnswerNumber = 4,
            imagePath = "pdd_01_04")
        insert(newQuestion)

        // pdd_01_05
        newQuestion = Question(
            text = "Вы намерены повернуть налево. Где следует остановиться, чтобы уступить дорогу легковому автомобилю?",
            firstAnswer = "1. Перед знаком.",
            secondAnswer = "2. Перед перекрестком у линии разметки.",
            thirdAnswer = "3. На перекрестке перед прерывистой линией разметки.",
            fourthAnswer = "4. В любом месте по усмотрению водителя.",
            correctAnswerNumber = 2,
            imagePath = "pdd_01_05")
        insert(newQuestion)

        // pdd_01_06
        newQuestion = Question(
            text = "Что означает мигание зеленого сигнала светофора?",
            firstAnswer = "1. Предупреждает о неисправности светофора.",
            secondAnswer = "2. Разрешает движение и информирует о том, что вскоре будет включен запрещающий сигнал.",
            thirdAnswer = "3. Запрещает дальнейшее движение.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_01_07
        newQuestion = Question(
            text = "Водитель обязан подавать сигналы световыми указателями поворота (рукой):",
            firstAnswer = "1. Перед началом движения или перестроением.",
            secondAnswer = "2. Перед поворотом или разворотом.",
            thirdAnswer = "3. Перед остановкой.",
            fourthAnswer = "4. Во всех перечисленных случаях.",
            correctAnswerNumber = 4)
        insert(newQuestion)

        // pdd_01_08
        newQuestion = Question(
            text = "Как Вам следует поступить при повороте направо?",
            firstAnswer = "1. Перестроиться на правую полосу, затем осуществить поворот.",
            secondAnswer = "2. Продолжить движение по второй полосе до перекрестка, затем повернуть.",
            thirdAnswer = "3. Возможны оба варианта действий.",
            correctAnswerNumber = 3,
            imagePath = "pdd_01_08")
        insert(newQuestion)

        // pdd_01_09
        newQuestion = Question(
            text = "По какой траектории Вам разрешено выполнить разворот?",
            firstAnswer = "1. Только по А.",
            secondAnswer = "2. Только по Б.",
            thirdAnswer = "3. По любой из указанных.",
            correctAnswerNumber = 1,
            imagePath = "pdd_01_09")
        insert(newQuestion)

        // pdd_01_10
        newQuestion = Question(
            text = "С какой скоростью Вы можете продолжить движение вне населенного пункта по левой полосе на легковом автомобиле?",
            firstAnswer = "1. Не более 50 км/ч.",
            secondAnswer = "2. Не менее 50 км/ч и не более 70 км/ч.",
            thirdAnswer = "3. Не менее 50 км/ч и не более 90 км/ч.",
            correctAnswerNumber = 3,
            imagePath = "pdd_01_10")
        insert(newQuestion)

        // pdd_01_11
        newQuestion = Question(
            text = "Можно ли водителю легкового автомобиля выполнить опережение грузовых автомобилей вне населенного пункта по такой траектории?",
            firstAnswer = "1. Можно.",
            secondAnswer = "2. Можно, если скорость грузовых автомобилей менее 30 км/ч.",
            thirdAnswer = "3. Нельзя.",
            correctAnswerNumber = 1,
            imagePath = "pdd_01_11")
        insert(newQuestion)

        // pdd_01_12
        newQuestion = Question(
            text = "В каком случае водителю разрешается поставить автомобиль на стоянку в указанном месте?",
            firstAnswer = "1. Только если расстояние до сплошной линии разметки не менее 3 м.",
            secondAnswer = "2. Только если расстояние до края пересекаемой проезжей части не менее 5 м.",
            thirdAnswer = "3. При соблюдении обоих перечисленных условий.",
            correctAnswerNumber = 3,
            imagePath = "pdd_01_12")
        insert(newQuestion)

        // pdd_01_13
        newQuestion = Question(
            text = "При повороте направо Вы должны уступить дорогу:",
            firstAnswer = "1. Только велосипедисту.",
            secondAnswer = "2. Только пешеходам.",
            thirdAnswer = "3. Пешеходам и велосипедисту.",
            fourthAnswer = "4. Никому.",
            correctAnswerNumber = 3,
            imagePath = "pdd_01_13")
        insert(newQuestion)

        // pdd_01_14
        newQuestion = Question(
            text = "Вы намерены проехать перекресток в прямом направлении. Кому Вы должны уступить дорогу?",
            firstAnswer = "1. Обоим трамваям.",
            secondAnswer = "2. Только трамваю А.",
            thirdAnswer = "3. Только трамваю Б.",
            fourthAnswer = "4. Никому.",
            correctAnswerNumber = 1,
            imagePath = "pdd_01_14")
        insert(newQuestion)

        // pdd_01_15
        newQuestion = Question(
            text = "Кому Вы обязаны уступить дорогу при повороте налево?",
            firstAnswer = "1. Только автобусу.",
            secondAnswer = "2. Только легковому автомобилю.",
            thirdAnswer = "3. Никому.",
            correctAnswerNumber = 3,
            imagePath = "pdd_01_15")
        insert(newQuestion)

        // pdd_01_16
        newQuestion = Question(
            text = "С какой максимальной скоростью можно продолжить движение за знаком?",
            firstAnswer = "1. 60 км/ч.",
            secondAnswer = "2. 50 км/ч.",
            thirdAnswer = "3. 30 км/ч.",
            fourthAnswer = "4. 20 км/ч.",
            correctAnswerNumber = 4,
            imagePath = "pdd_01_16")
        insert(newQuestion)

        // pdd_01_17
        newQuestion = Question(
            text = "Для перевозки людей на мотоцикле водитель должен иметь водительское удостоверение на право управления транспортными средствами:",
            firstAnswer = "1. Категории «A» или подкатегории «A1».",
            secondAnswer = "2. Любой категории или подкатегории в течение двух и более лет.",
            thirdAnswer = "3. Только категории «A» или подкатегории «A1» в течение двух и более лет.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_01_18
        newQuestion = Question(
            text = "При какой неисправности разрешается эксплуатация транспортного средства?",
            firstAnswer = "1. Не работают запорные устройства топливных баков.",
            secondAnswer = "2. Не работают механизм регулировки и фиксирующее устройство сиденья водителя.",
            thirdAnswer = "3. Не работает устройство обдува ветрового стекла.",
            fourthAnswer = "4. Не работает стеклоподъемник.",
            correctAnswerNumber = 4)
        insert(newQuestion)

        // pdd_01_19
        newQuestion = Question(
            text = "В случае, когда правые колеса автомобиля наезжают на неукрепленную влажную обочину, рекомендуется:",
            firstAnswer = "1. Затормозить и полностью остановиться.",
            secondAnswer = "2. Затормозить и плавно направить автомобиль на проезжую часть.",
            thirdAnswer = "3. Не прибегая к торможению, плавно направить автомобиль на проезжую часть.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_01_20
        newQuestion = Question(
            text = "Что понимается под временем реакции водителя?",
            firstAnswer = "1. Время с момента обнаружения водителем опасности до полной остановки транспортного средства.",
            secondAnswer = "2. Время с момента обнаружения водителем опасности до начала принятия мер по ее избежанию.",
            thirdAnswer = "3. Время, необходимое для переноса ноги с педали управления подачей топлива на педаль тормоза.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_02_01
        newQuestion = Question(
            text = "Сколько полос для движения имеет данная дорога?",
            firstAnswer = "1. Две.",
            secondAnswer = "2. Четыре.",
            thirdAnswer = "3. Пять.",
            correctAnswerNumber = 2,
            imagePath = "pdd_02_01")
        insert(newQuestion)

        // pdd_02_02
        newQuestion = Question(
            text = "Можно ли Вам въехать на мост первым?",
            firstAnswer = "1. Можно.",
            secondAnswer = "2. Можно, если Вы не затрудните движение встречному автомобилю.",
            thirdAnswer = "3. Нельзя.",
            correctAnswerNumber = 1,
            imagePath = "pdd_02_02")
        insert(newQuestion)

        // pdd_02_03
        newQuestion = Question(
            text = "Разрешено ли Вам произвести остановку для посадки пассажира?",
            firstAnswer = "1. Разрешено.",
            secondAnswer = "2. Разрешено только по четным числам месяца.",
            thirdAnswer = "3. Разрешено только по нечетным числам месяца.",
            fourthAnswer = "4. Запрещено.",
            correctAnswerNumber = 1,
            imagePath = "pdd_02_03")
        insert(newQuestion)

        // pdd_02_04
        newQuestion = Question(
            text = "Что запрещено в зоне действия этого знака?",
            firstAnswer = "1. Движение любых транспортных средств.",
            secondAnswer = "2. Движение всех транспортных средств со скоростью не более 20 км/ч.",
            thirdAnswer = "3. Движение механических транспортных средств.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_04")
        insert(newQuestion)

        // pdd_02_05
        newQuestion = Question(
            text = "Разрешен ли Вам выезд на полосу с реверсивным движением, если реверсивный светофор выключен?",
            firstAnswer = "1. Разрешен.",
            secondAnswer = "2. Разрешен, если скорость автобуса менее 30 км/ч.",
            thirdAnswer = "3. Запрещен.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_05")
        insert(newQuestion)

        // pdd_02_06
        newQuestion = Question(
            text = "Информационная световая секция в виде силуэта пешехода и стрелки с мигающим сигналом бело-лунного цвета, расположенная под светофором, информирует водителя о том, что:",
            firstAnswer = "1. На пешеходном переходе, в направлении которого он поворачивает, включен сигнал светофора, разрешающий движение пешеходам.",
            secondAnswer = "2. На пешеходном переходе, в направлении которого он поворачивает, включен сигнал светофора, запрещающий движение пешеходам.",
            thirdAnswer = "3. Он поворачивает в направлении пешеходного перехода.",
            correctAnswerNumber = 1,
            imagePath = "pdd_02_06")
        insert(newQuestion)

        // pdd_02_07
        newQuestion = Question(
            text = "Поднятая вверх рука водителя легкового автомобиля является сигналом, информирующим Вас о его намерении:",
            firstAnswer = "1. Повернуть направо.",
            secondAnswer = "2. Продолжить движение прямо.",
            thirdAnswer = "3. Снизить скорость, чтобы остановиться и уступить дорогу мотоциклу.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_07")
        insert(newQuestion)

        // pdd_02_08
        newQuestion = Question(
            text = "Двигаясь по левой полосе, водитель намерен перестроиться на правую. На каком из рисунков показана ситуация, в которой он обязан уступить дорогу?",
            firstAnswer = "1. На левом.",
            secondAnswer = "2. На правом.",
            thirdAnswer = "3. На обоих.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_08")
        insert(newQuestion)

        // pdd_02_09
        newQuestion = Question(
            text = "Можно ли Вам выполнить разворот в этом месте?",
            firstAnswer = "1. Можно.",
            secondAnswer = "2. Можно только при отсутствии приближающегося поезда.",
            thirdAnswer = "3. Нельзя.",
            correctAnswerNumber = 1,
            imagePath = "pdd_02_09")
        insert(newQuestion)

        // pdd_02_10
        newQuestion = Question(
            text = "В каких случаях разрешается наезжать на прерывистые линии разметки, разделяющие проезжую часть на полосы движения?",
            firstAnswer = "1. Только если на дороге нет других транспортных средств.",
            secondAnswer = "2. Только при движении в темное время суток.",
            thirdAnswer = "3. Только при перестроении.",
            fourthAnswer = "4. Во всех перечисленных случаях.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_02_11
        newQuestion = Question(
            text = "Разрешено ли Вам обогнать мотоцикл?",
            firstAnswer = "1. Разрешено.",
            secondAnswer = "2. Разрешено, если водитель мотоцикла снизил скорость.",
            thirdAnswer = "3. Запрещено.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_11")
        insert(newQuestion)

        // pdd_02_12
        newQuestion = Question(
            text = "Разрешается ли Вам остановиться в указанном месте?",
            firstAnswer = "1. Разрешается.",
            secondAnswer = "2. Разрешается, если автомобиль будет находиться не ближе 5 м от края пересекаемой проезжей части.",
            thirdAnswer = "3. Запрещается.",
            correctAnswerNumber = 2,
            imagePath = "pdd_02_12")
        insert(newQuestion)

        // pdd_02_13
        newQuestion = Question(
            text = "Вы намерены повернуть налево. Кому Вы должны уступить дорогу?",
            firstAnswer = "1. Только пешеходам.",
            secondAnswer = "2. Только автобусу.",
            thirdAnswer = "3. Автобусу и пешеходам.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_13")
        insert(newQuestion)

        // pdd_02_14
        newQuestion = Question(
            text = "В каком случае Вы имеете преимущество?",
            firstAnswer = "1. Только при повороте направо.",
            secondAnswer = "2. Только при повороте налево.",
            thirdAnswer = "3. В обоих перечисленных случаях.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_14")
        insert(newQuestion)

        // pdd_02_15
        newQuestion = Question(
            text = "Обязан ли водитель мотоцикла уступить Вам дорогу?",
            firstAnswer = "1. Обязан.",
            secondAnswer = "2. Не обязан.",
            correctAnswerNumber = 1,
            imagePath = "pdd_02_15")
        insert(newQuestion)

        // pdd_02_16
        newQuestion = Question(
            text = "Разрешается ли водителю выполнить объезд грузового автомобиля?",
            firstAnswer = "1. Разрешается.",
            secondAnswer = "2. Разрешается, если между шлагбаумом и остановившимся грузовым автомобилем расстояние более 5 м.",
            thirdAnswer = "3. Запрещается.",
            correctAnswerNumber = 3,
            imagePath = "pdd_02_16")
        insert(newQuestion)

        // pdd_02_17
        newQuestion = Question(
            text = "В каких из перечисленных случаев запрещена буксировка на гибкой сцепке?",
            firstAnswer = "1. Только на горных дорогах.",
            secondAnswer = "2. Только в гололедицу.",
            thirdAnswer = "3. Только в темное время суток и в условиях недостаточной видимости.",
            fourthAnswer = "4. Во всех перечисленных случаях.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_02_18
        newQuestion = Question(
            text = "Запрещается эксплуатация мототранспортных средств (категории L), если остаточная глубина рисунка протектора шин (при отсутствии индикаторов износа) составляет не более:",
            firstAnswer = "1. 0,8 мм.",
            secondAnswer = "2. 1,0 мм.",
            thirdAnswer = "3. 1,6 мм.",
            fourthAnswer = "4. 2,0 мм.",
            correctAnswerNumber = 1)
        insert(newQuestion)

        // pdd_02_19
        newQuestion = Question(
            text = "Исключает ли антиблокировочная тормозная система возможность возникновения заноса или сноса при прохождении поворота?",
            firstAnswer = "1. Полностью исключает возможность возникновения только заноса.",
            secondAnswer = "2. Полностью исключает возможность возникновения только сноса.",
            thirdAnswer = "3. Не исключает возможность возникновения сноса или заноса.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_02_20
        newQuestion = Question(
            text = "В каких случаях следует начинать сердечно-легочную реанимацию пострадавшего?",
            firstAnswer = "1. При наличии болей в области сердца и затрудненного дыхания.",
            secondAnswer = "2. При отсутствии у пострадавшего сознания, независимо от наличия дыхания.",
            thirdAnswer = "3. При отсутствии у пострадавшего сознания, дыхания и кровообращения.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_03_01
        newQuestion = Question(
            text = "Выезжая с грунтовой дороги на перекресток, Вы попадаете:",
            firstAnswer = "1. На главную дорогу.",
            secondAnswer = "2. На равнозначную дорогу, поскольку отсутствуют знаки приоритета.",
            thirdAnswer = "3. На равнозначную дорогу, поскольку проезжая часть имеет твердое покрытие перед перекрестком.",
            correctAnswerNumber = 1,
            imagePath = "pdd_03_01")
        insert(newQuestion)

        // pdd_03_02
        newQuestion = Question(
            text = "Где Вы должны остановиться?",
            firstAnswer = "1. Перед знаком (А).",
            secondAnswer = "2. Перед перекрестком (Б).",
            thirdAnswer = "3. Перед краем пересекаемой проезжей части (В).",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_02")
        insert(newQuestion)

        // pdd_03_03
        newQuestion = Question(
            text = "Вам необходимо двигаться со скоростью не более 40 км/ч:",
            firstAnswer = "1. Только во время дождя.",
            secondAnswer = "2. Во время выпадения осадков (дождя, града, снега).",
            thirdAnswer = "3. Во всех случаях, когда покрытие проезжей части влажное.",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_03")
        insert(newQuestion)

        // pdd_03_04
        newQuestion = Question(
            text = "Какой из указанных знаков устанавливается в начале дороги с односторонним движением?",
            firstAnswer = "1. Только А.",
            secondAnswer = "2. Только Б.",
            thirdAnswer = "3. Б или Г.",
            fourthAnswer = "4. Б или В.",
            correctAnswerNumber = 2,
            imagePath = "pdd_03_04")
        insert(newQuestion)

        // pdd_03_05
        newQuestion = Question(
            text = "Можно ли Вам остановиться в этом месте для посадки или высадки пассажиров?",
            firstAnswer = "1. Можно.",
            secondAnswer = "2. Можно, если при этом не будут созданы помехи движению маршрутных транспортных средств.",
            thirdAnswer = "3. Нельзя.",
            correctAnswerNumber = 2,
            imagePath = "pdd_03_05")
        insert(newQuestion)

        // pdd_03_06
        newQuestion = Question(
            text = "При повороте направо Вы:",
            firstAnswer = "1. Имеете право проехать перекресток первым.",
            secondAnswer = "2. Должны уступить дорогу только пешеходам.",
            thirdAnswer = "3. Должны уступить дорогу автомобилю с включенными проблесковым маячком и специальным звуковым сигналом, а также пешеходам.",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_06")
        insert(newQuestion)

        // pdd_03_07
        newQuestion = Question(
            text = "В каких случаях водитель не должен подавать сигнал указателями поворота?",
            firstAnswer = "1. Только при отсутствии на дороге других участников движения.",
            secondAnswer = "2. Только если сигнал может ввести в заблуждение других участников движения.",
            thirdAnswer = "3. В обоих перечисленных случаях.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_03_08
        newQuestion = Question(
            text = "Вам разрешено выполнить поворот направо:",
            firstAnswer = "1. Только по траектории А.",
            secondAnswer = "2. Только по траектории Б.",
            thirdAnswer = "3. По любой траектории из указанных.",
            correctAnswerNumber = 1,
            imagePath = "pdd_03_08")
        insert(newQuestion)

        // pdd_03_09
        newQuestion = Question(
            text = "Разрешается ли Вам выполнить разворот на перекрестке по указанной траектории?",
            firstAnswer = "1. Разрешается.",
            secondAnswer = "2. Разрешается, если видимость дороги не менее 100 м.",
            thirdAnswer = "3. Запрещается.",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_09")
        insert(newQuestion)

        // pdd_03_10
        newQuestion = Question(
            text = "По какой полосе Вы имеете право двигаться с максимальной разрешенной скоростью вне населенных пунктов?",
            firstAnswer = "1. Только по правой.",
            secondAnswer = "2. Только по левой.",
            thirdAnswer = "3. По любой.",
            correctAnswerNumber = 1,
            imagePath = "pdd_03_10")
        insert(newQuestion)

        // pdd_03_11
        newQuestion = Question(
            text = "В каком случае водитель может начать обгон, если такой маневр на данном участке дороги не запрещен?",
            firstAnswer = "1. Только если полоса, предназначенная для встречного движения, свободна на достаточном для обгона расстоянии.",
            secondAnswer = "2. Только если его транспортное средство никто не обгоняет.",
            thirdAnswer = "3. В случае, если выполнены оба условия.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_03_12
        newQuestion = Question(
            text = "Кто из водителей нарушил правила стоянки?",
            firstAnswer = "1. Оба.",
            secondAnswer = "2. Только водитель автомобиля.",
            thirdAnswer = "3. Только водитель мотоцикла.",
            fourthAnswer = "4. Никто не нарушил.",
            correctAnswerNumber = 2,
            imagePath = "pdd_03_12")
        insert(newQuestion)

        // pdd_03_13
        newQuestion = Question(
            text = "При движении прямо Вы:",
            firstAnswer = "1. Должны остановиться перед стоп-линией.",
            secondAnswer = "2. Можете продолжить движение через перекресток без остановки.",
            thirdAnswer = "3. Должны уступить дорогу транспортным средствам, движущимся с других направлений.",
            correctAnswerNumber = 2,
            imagePath = "pdd_03_13")
        insert(newQuestion)

        // pdd_03_14
        newQuestion = Question(
            text = "Вы намерены повернуть направо. Ваши действия?",
            firstAnswer = "1. Проедете перекресток первым.",
            secondAnswer = "2. Уступите дорогу легковому автомобилю.",
            thirdAnswer = "3. Уступите дорогу обоим транспортным средствам.",
            correctAnswerNumber = 1,
            imagePath = "pdd_03_14")
        insert(newQuestion)

        // pdd_03_15
        newQuestion = Question(
            text = "Кому Вы обязаны уступить дорогу при повороте налево?",
            firstAnswer = "1. Трамваям А и Б.",
            secondAnswer = "2. Трамваю А и легковому автомобилю.",
            thirdAnswer = "3. Только трамваю А.",
            fourthAnswer = "4. Никому.",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_15")
        insert(newQuestion)

        // pdd_03_16
        newQuestion = Question(
            text = "Кто из водителей нарушил правила остановки?",
            firstAnswer = "1. Только водитель легкового автомобиля.",
            secondAnswer = "2. Только водитель грузового автомобиля.",
            thirdAnswer = "3. Оба.",
            correctAnswerNumber = 3,
            imagePath = "pdd_03_16")
        insert(newQuestion)

        // pdd_03_17
        newQuestion = Question(
            text = "Какое оборудование должно иметь механическое транспортное средство, используемое для обучения вождению?",
            firstAnswer = "1. Дополнительные педали привода сцепления (кроме транспортных средств с автоматической трансмиссией) и тормоза.",
            secondAnswer = "2. Зеркало заднего вида для обучающего вождению.",
            thirdAnswer = "3. Опознавательные знаки «Учебное транспортное средство».",
            fourthAnswer = "4. Все перечисленное оборудование.",
            correctAnswerNumber = 4)
        insert(newQuestion)

        // pdd_03_18
        newQuestion = Question(
            text = "В каких случаях запрещается эксплуатация мотоцикла?",
            firstAnswer = "1. Подножки или рукоятки пассажиров на седле не имеют прорезиненного покрытия.",
            secondAnswer = "2. Имеется люфт в соединениях рамы мотоцикла с рамой бокового прицепа.",
            thirdAnswer = "3. Отсутствует огнетушитель.",
            correctAnswerNumber = 2)
        insert(newQuestion)

        // pdd_03_19
        newQuestion = Question(
            text = "На повороте возник занос задней оси переднеприводного автомобиля. Ваши действия?",
            firstAnswer = "1. Уменьшите подачу топлива, рулевым колесом стабилизируете движение.",
            secondAnswer = "2. Притормозите и повернете рулевое колесо в сторону заноса.",
            thirdAnswer = "3. Слегка увеличите подачу топлива, корректируя направление движения рулевым колесом.",
            fourthAnswer = "4. Значительно увеличите подачу топлива, не меняя положения рулевого колеса.",
            correctAnswerNumber = 3)
        insert(newQuestion)

        // pdd_03_20
        newQuestion = Question(
            text = "Какие сведения необходимо сообщить диспетчеру для вызова скорой медицинской помощи при дорожно-транспортном происшествии (ДТП)?",
            firstAnswer = "1. Указать общеизвестные ориентиры, ближайшие к месту ДТП. Сообщить о количестве пострадавших, указать их пол и возраст.",
            secondAnswer = "2. Указать улицу и номер дома, ближайшего к месту ДТП. Сообщить, кто пострадал в ДТП (пешеход, водитель автомобиля или пассажиры), и описать травмы, которые они получили.",
            thirdAnswer = "3. Указать место ДТП (назвать улицу, номер дома и общеизвестные ориентиры, ближайшие к месту ДТП). Сообщить: количество пострадавших, их пол, примерный возраст, наличие у них сознания, дыхания, кровообращения, а также сильного кровотечения, переломов и других травм. Дождаться сообщения диспетчера о том, что вызов принят.",
            correctAnswerNumber = 3)
        insert(newQuestion)
    }
}