package com.example.myapplication

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.rotationMatrix
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Double.max
import java.lang.Integer.min
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    lateinit var helloTxt: TextView
    lateinit var logoTxt: TextView
    lateinit var playButton: Button
    lateinit var tryButton: Button
    lateinit var guessEdit: EditText
    lateinit var life: LinearLayout
    lateinit var scoreBar: RelativeLayout
    lateinit var score: TextView
    lateinit var takes: TextView
    lateinit var continueButton: Button
    lateinit var enemyimg: ImageView
    lateinit var animationEnemy: AnimationDrawable
    lateinit var enemyBg: ImageView
    lateinit var weaknessTxt: TextView
    lateinit var leaderboardButton: Button
    lateinit var registerButton: Button
    lateinit var loginButton: Button
    var loggedUser: String? = null





    private fun takeHeart(health: Int?, hearts: List<ImageView>){
            if(health != null && health >= 0){ hearts[health].visibility = View.INVISIBLE}
    }


    private fun giveHeart(health: Int?, hearts: List<ImageView>){
        if(health != null && health >= 0) {
            for (i in 1..health){ hearts[i].visibility = View.VISIBLE }
        }
    }

    private fun calcScore(takesPoints: Int?): Int = when(takesPoints){
            1 -> 5
            in 2..4 -> 3
            in 5..6 -> 2
            in 7..10 -> 1
            else -> 0
        }


    private fun setGame(scorePoints: Int?, takesPoints: Int?, hearts: List<ImageView>, health: Int?){

        score.text = scorePoints?.toString()
        takes.text = takesPoints?.toString()
        guessEdit.text.clear()

        health?.let {for(i in 1.. it){
            hearts[i-1].visibility = View.VISIBLE}
        }
    }
    private fun setView(){
        helloTxt.visibility = View.INVISIBLE
        logoTxt.visibility = View.INVISIBLE
        playButton.visibility = View.INVISIBLE
        tryButton.visibility = View.VISIBLE
        guessEdit.visibility= View.VISIBLE
        life.visibility = View.VISIBLE
        scoreBar.visibility = View.VISIBLE
        continueButton.visibility = View.INVISIBLE
        enemyimg.visibility = View.VISIBLE
        enemyBg.visibility = View.VISIBLE
        weaknessTxt.visibility = View.VISIBLE
        registerButton.visibility = View.INVISIBLE
        loginButton.visibility = View.INVISIBLE
    }

    private fun setRecord(saveScore: Int?, saveTakes: Int?, saveLife: Int?, saveGoal: Int?, userName: String){
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val edit = sharedScore.edit()
        if(saveScore != null && saveLife != null && saveTakes != null && saveGoal != null){
        edit.putInt("score", saveScore)
        edit.putInt("takes", saveTakes)
        edit.putInt("life", saveLife)
        edit.putInt("goal", saveGoal)
        edit.putString("user", userName)
        edit.apply()
        }
    }


    private fun checkRecord(){
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val scoreCheck = sharedScore.getInt("score", 0)
        val takeCheck = sharedScore.getInt("takes", 0)
        val lifeCheck = sharedScore.getInt("life", 0)
        val userSave = sharedScore.getString("user", 0.toString())

        if(scoreCheck != null || takeCheck != null || lifeCheck != null){
            if(scoreCheck != 0 || takeCheck != 0 || lifeCheck != 10){
                if(userSave == loggedUser.toString()){
                    continueButton.visibility = View.VISIBLE}
            }
        }
    }

    private fun getScore(): Int {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getInt("score", 0)
    }
    private fun getTakes(): Int {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getInt("takes", 0)
    }
    private fun getLife(): Int {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getInt("life", 0)
    }
    private fun getGoal(): Int {
        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        return sharedScore.getInt("goal", 0)
    }

    private fun hideSystemBars() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
    private fun randomGenerator(): Int{
        return Random.nextInt(0,20)
    }


    private fun finishGame(){
        helloTxt.visibility = View.VISIBLE
        logoTxt.visibility = View.VISIBLE
        playButton.visibility = View.VISIBLE
        tryButton.visibility = View.INVISIBLE
        guessEdit.visibility= View.INVISIBLE
        life.visibility = View.INVISIBLE
        scoreBar.visibility = View.INVISIBLE
        enemyBg.visibility = View.INVISIBLE
        weaknessTxt.visibility = View.INVISIBLE
        enemyimg.visibility = View.INVISIBLE
        registerButton.visibility = View.VISIBLE
        loginButton.visibility = View.VISIBLE


        tryButton.animate().apply{
            translationYBy(-350f)
            duration=  1
        }.start()



    }


    override fun onResume() {
        super.onResume()

        loggedUser = intent.extras?.getString("username")

        if (!loggedUser.isNullOrEmpty()) {
            loginButton.setText("Log Out ($loggedUser)")
            checkRecord()
        }

    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        hideSystemBars()


        val relativeLayout = findViewById<RelativeLayout>(R.id.mainactivityBackground)
        val animationDrawable = relativeLayout.background as AnimationDrawable
        animationDrawable.apply{

            setExitFadeDuration(2000)

        }.start()

        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val defeat_anim = AnimationUtils.loadAnimation(this, R.anim.defeat_anim)
        val up_float = AnimationUtils.loadAnimation(this, R.anim.up_float)
        val fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val fade_out_quick = AnimationUtils.loadAnimation(this, R.anim.fade_out_quick)
        val fade_in_quick = AnimationUtils.loadAnimation(this, R.anim.fade_in_quick)
        val up_float_quick = AnimationUtils.loadAnimation(this, R.anim.up_float_quick)
        val back_float_quick = AnimationUtils.loadAnimation(this, R.anim.back_float_quick)




            helloTxt = findViewById(R.id.Hello)
            playButton = findViewById(R.id.playButton)
            tryButton = findViewById(R.id.tryButton)
            guessEdit = findViewById(R.id.guessEdit)
            logoTxt = findViewById(R.id.logo)
            life = findViewById(R.id.lifes)
            scoreBar = findViewById(R.id.ScorePanel)
            score = findViewById(R.id.ScorePoints)
            takes = findViewById(R.id.takesPoints)
            continueButton = findViewById(R.id.continueButton)
            weaknessTxt =findViewById(R.id.weakness)
            enemyBg = findViewById(R.id.enemyBackground)
            enemyimg = findViewById(R.id.enemy)
            leaderboardButton = findViewById(R.id.trophyButton)
            registerButton = findViewById(R.id.registerButton)
            loginButton = findViewById(R.id.loginButton)

        val backLeaderboard = findViewById<Button>(R.id.cancelButton)
        val leaderboard_sheet = findViewById<RelativeLayout>(R.id.leaderboard_sheet)



            animationEnemy = enemyimg.background as AnimationDrawable


         val enemyBgDrawable = enemyBg.background as AnimationDrawable

            var goal: Int? = null
            var scorePoints: Int? = 0
            var lifePoints: Int? = 10
            var takePoints: Int? = 0
        

            val heartBar = listOf<ImageView>(findViewById(R.id.life1),
                findViewById(R.id.life2),
                findViewById(R.id.life3),
                findViewById(R.id.life4),
                findViewById(R.id.life5),
                findViewById(R.id.life6),
                findViewById(R.id.life7),
                findViewById(R.id.life8),
                findViewById(R.id.life9),
                findViewById(R.id.life10))

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Game Over")
            builder.setCancelable(false)




            playButton.setOnClickListener{
                if (!loggedUser.isNullOrEmpty()) {





                goal = randomGenerator()
                scorePoints = 0
                takePoints = 0
                lifePoints = 10

                setRecord(scorePoints, takePoints, lifePoints, goal, loggedUser.toString())

                setGame(scorePoints, takePoints, heartBar, lifePoints)

                enemyBgDrawable.apply{

                    setEnterFadeDuration(2000)
                    setExitFadeDuration(2000)

                }.start()

                enemyBg.startAnimation(fade_in)
                enemyimg.startAnimation(stb)
                animationEnemy.start()
                logoTxt.startAnimation(fade_out)
                life.startAnimation(fade_in)
                scoreBar.startAnimation(fade_in)
                score.startAnimation(fade_in)
                takes.startAnimation(fade_in)
                weaknessTxt.startAnimation(fade_in)

                guessEdit.startAnimation(fade_in)
                animationDrawable.stop()



                setView()

                tryButton.animate().apply{
                    translationYBy(350f)
                    duration = 800
                }.start()

                }
                else{
                    Toast.makeText(this, "Log in first!", Toast.LENGTH_SHORT).show()
                }

            }

            continueButton.setOnClickListener(){

                if (!loggedUser.isNullOrEmpty()) {


                    scorePoints = getScore()
                    takePoints = getTakes()
                    lifePoints = getLife()
                    goal = getGoal()


                    setGame(scorePoints, takePoints, heartBar, lifePoints)

                    enemyBgDrawable.apply {

                        setEnterFadeDuration(2000)
                        setExitFadeDuration(2000)

                    }.start()
                    enemyBg.startAnimation(fade_in)
                    enemyimg.startAnimation(stb)
                    animationEnemy.start()
                    logoTxt.startAnimation(fade_out)
                    life.startAnimation(fade_in)
                    scoreBar.startAnimation(fade_in)
                    score.startAnimation(fade_in)
                    takes.startAnimation(fade_in)
                    weaknessTxt.startAnimation(fade_in)
                    guessEdit.startAnimation(fade_in)
                    animationDrawable.stop()

                    setView()

                    tryButton.animate().apply {
                        translationYBy(350f)
                        duration = 800
                    }.start()

                }
                else{
                    Toast.makeText(this, "Log in first!", Toast.LENGTH_SHORT).show()
                }

            }

        leaderboardButton.setOnClickListener{
                val myDB = DBHelper(this)
                val arrayAdapter: ArrayAdapter<*>
                val users = myDB.getUserScores()

                var users_ranked= ArrayList<String>()

                val subUserList = users.subList(0,min(users.size, 10))

               val sortedDatesDescending =  subUserList.sortedWith(compareBy{ it.score.toInt() }).reversed()

            sortedDatesDescending.forEach {
                    users_ranked.add("${sortedDatesDescending.indexOf(it)+1}. ${it.username} ${it.score}")
                }


                var mListView = findViewById<ListView>(R.id.leaderboard_list)
                arrayAdapter = ArrayAdapter(this,
                    R.layout.leaderboard_textview, users_ranked)
                mListView.adapter = arrayAdapter



                leaderboardButton.startAnimation(fade_out_quick)
                leaderboardButton.visibility = View.INVISIBLE


            if(!tryButton.isShown) {
                loginButton.startAnimation(fade_out_quick)
                loginButton.visibility = View.INVISIBLE

                registerButton.startAnimation(fade_out_quick)
                registerButton.visibility = View.INVISIBLE
            }

                leaderboard_sheet.startAnimation(up_float_quick)
                leaderboard_sheet.visibility =View.VISIBLE

            }
            loginButton.setOnClickListener{

                if (!loggedUser.isNullOrEmpty()) {
                    loggedUser = null
                    loginButton.setText("Login")
                    intent.putExtra("username", "")
                    if(continueButton.isShown){
                        continueButton.visibility = View.INVISIBLE
                    }

                }
                else{
                val logIntent = Intent(this, LoginActivity::class.java)
                startActivity(logIntent)
                onPause()}

            }
            registerButton.setOnClickListener {
                val signIntent = Intent(this, RegisterActivity::class.java)
                startActivity(signIntent)
                onPause()
            }

            backLeaderboard.setOnClickListener{


                leaderboard_sheet.animate().apply{
                    leaderboard_sheet.startAnimation(back_float_quick)
                    leaderboard_sheet.visibility = View.GONE
                }.withEndAction {
                    leaderboardButton.startAnimation(fade_in_quick)
                    leaderboardButton.visibility = View.VISIBLE

                    if(!tryButton.isShown){
                    loginButton.startAnimation(fade_in_quick)
                    loginButton. visibility = View.VISIBLE

                    registerButton.startAnimation(fade_in_quick)
                    registerButton. visibility = View.VISIBLE}

                }.start()

            }


            tryButton.setOnClickListener{

                var shoot: Int? = guessEdit.text.toString().toIntOrNull()
                val dialog: AlertDialog = builder.create()


                when{
                    shoot == null -> Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
                    shoot !in 0..20 -> Toast.makeText(this, "Only from 0 to 20", Toast.LENGTH_SHORT).show()

                    shoot > goal ?:-1 -> {

                        lifePoints= lifePoints?.dec()
                        enemyimg.animate().apply {
                            tryButton.isClickable = false
                            translationYBy(-300f)
                            duration = 150

                        }.withEndAction {
                            takeHeart( lifePoints, heartBar)
                            enemyimg.animate().apply {
                                translationYBy(300f)
                                duration =500
                            }.withEndAction { tryButton.isClickable = true }
                        }



                        takePoints = takePoints?.inc()
                        takes.text = takePoints?.toString()



                        setRecord(scorePoints, takePoints, lifePoints, goal, loggedUser.toString())

                        if(lifePoints != 0){Toast.makeText(this, "Try a lower number", Toast.LENGTH_SHORT).show()}
                    }

                    shoot < goal ?:-1 ->{
                        lifePoints= lifePoints?.dec()

                        enemyimg.animate().apply {
                            tryButton.isClickable = false
                            translationYBy(-300f)
                            duration = 150


                        }.withEndAction {
                            takeHeart(lifePoints, heartBar)
                            enemyimg.animate().apply {

                                translationYBy(300f)
                                duration =300
                            }.withEndAction {
                                tryButton.isClickable = true
                            }
                        }


                        takePoints = takePoints?.inc()
                        takes.text = takePoints?.toString()



                        setRecord(scorePoints, takePoints, lifePoints, goal, loggedUser.toString())

                        if(lifePoints != 0){Toast.makeText(this, "Try a higher number", Toast.LENGTH_SHORT).show()}
                    }

                    shoot == goal -> {

                        enemyimg.animate().apply {
                            enemyimg.visibility= View.INVISIBLE
                            tryButton.isClickable = false
                            duration = 1000
                            rotationYBy(1080f)
                            enemyimg.startAnimation(defeat_anim)
                        }.withEndAction{
                            enemyimg.visibility= View.VISIBLE
                            enemyimg.startAnimation(up_float)
                            tryButton.isClickable = true

                        }



                        takePoints = takePoints?.inc()

                        scorePoints = ((scorePoints?:0) + calcScore(takePoints))
                        score.text = scorePoints.toString()

                        if (lifePoints != 10){
                            giveHeart(lifePoints, heartBar)
                            lifePoints = lifePoints?.inc()
                        }

                        takePoints = 0
                        takes.text = takePoints?.toString()

                        setRecord(scorePoints, takePoints, lifePoints, goal, loggedUser.toString())

                        Toast.makeText(this, "You guessed it!", Toast.LENGTH_SHORT).show()
                        goal = randomGenerator()
                    }

                }
                if(lifePoints == 0 ){

                    val dbHelper = DBHelper(this)
                    dbHelper.updateUserScore(loggedUser.toString(), scorePoints.toString())

                    builder.setMessage("You Scored $scorePoints Points")
                    setRecord(0, 0, 10, 0, loggedUser.toString())
                    animationDrawable.start()


                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int -> finishGame()}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    enemyBgDrawable.stop()
                    animationEnemy.stop()

                }

            }
        }

    }

