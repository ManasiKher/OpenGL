package dayeleven.com.lrm.kotlinday11

import android.graphics.*
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import android.graphics.BitmapFactory
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Color.LTGRAY
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.filippudak.ProgressPieView.ProgressPieView


class MainActivity : AppCompatActivity() {

    private lateinit var gradientSet:GradientSet
    private lateinit var bitmap: Bitmap
    private lateinit var bitMapOrg:Drawable
    private val SIZE = 96
    private val MARGIN = 8

    private var colorMatrix: ColorMatrix? = null
    private var cmFilter: ColorMatrixColorFilter? = null
    private var cmPaint: Paint? = null

    //These member variables are responsible for drawing the Bitmap
    private var cv: Canvas? = null
    private var imgBitmap: Bitmap? = null
    private var canvasBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //gradientSet= GradientSet("dayeleven.com.lrm.kotlinday11",seek_gradient.id)

        //gradientSet.apply(this@MainActivity,seek_gradient)
        createGradient()
        checkForGradientProgress()
        //view_saturation.setBackgroundResource(R.drawable.cartoon_image)
        bitMapOrg= resources.getDrawable(R.drawable.cartoon_image) as Drawable
        checkForSaturationProgress()
        drawacircleWithCanvas()

    }

    fun createGradient()
    {
        val sf = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(0f, 0f, width.toFloat(), height.toFloat(),
                        intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW),
                        floatArrayOf(0.2f, 0.5f, .56f, 2f), Shader.TileMode.MIRROR)
            }
        }

        val p = PaintDrawable()
        p.shape = RectShape()
        p.shaderFactory = sf
        view_gradient.setBackgroundDrawable(p )
    }


    fun checkForGradientProgress()
    {
        seek_gradient.max = 200
        var progress=seek_gradient.progress
        colorMatrix = ColorMatrix()
        //Initialize the ColorMatrixColorFilter object
        cmFilter = ColorMatrixColorFilter(this.colorMatrix)

        //Initialize the cmPaint
        cmPaint = Paint()
        //Set 'cmFilter' as the color filter of this paint
        cmPaint?.colorFilter = cmFilter

        //Initialize the 'imgBitmap' by decoding the 'exampleimage.png' file
        imgBitmap = BitmapFactory.decodeResource(resources, R.drawable.cartoon_image)
        //Create a new mutable Bitmap, with the same width and height as the 'imgBitmap'
        canvasBitmap = Bitmap.createBitmap(400, 180, Bitmap.Config.RGB_565)
        //Initialize the canvas assigning the mutable Bitmap to it
        cv = Canvas(canvasBitmap)

        seek_gradient.progress= 100
        seek_gradient.max=100
        seek_gradient.keyProgressIncrement=1
        seek_gradient.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //createGradient()
                Log.d("Values ","$p1")
                createColorShades(p1)
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        } )
    }


    fun checkForSaturationProgress()
    {
        seek_gradient.max = 200
        seek_saturation.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //createGradient()
                Log.d("Values ","$p1")
                //createColorShades(p1)

                updateProgressValue(p1.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        } )
    }

    fun createColorShades(value:Int)
    {
        val hsv = FloatArray(3)
        var color = Color.rgb(value/10*Color.RED,value/5*Color.GREEN,value/10*Color.BLUE)
        Color.colorToHSV(color, hsv)
        hsv[2] *= .8f // value component
        color = Color.HSVToColor(hsv)
        view_gradient.setBackgroundColor(color)

    }


    fun updateProgressValue(progress:Float)
    {
        colorMatrix?.setSaturation(progress / 100)
        //Create a new ColorMatrixColorFilter with the recently altered colorMatrix
        cmFilter = ColorMatrixColorFilter(colorMatrix)

        //Assign the ColorMatrix to the paint object again
        cmPaint?.setColorFilter(cmFilter)

        //Draw the Bitmap into the mutable Bitmap using the canvas. Don't forget to pass the Paint as the last parameter
        cv?.drawBitmap(imgBitmap, 0.0f, 0.0f, cmPaint)
        //Set the mutalbe Bitmap to be rendered by the ImageView
        view_saturation.setImageBitmap(canvasBitmap)
    }


    fun drawacircleWithCanvas()
    {

        view_progress_pie.setProgress(20)


        // Default version
        view_progress_pie.setOnProgressListener(object : ProgressPieView.OnProgressListener {
            override fun onProgressChanged(progress: Int, max: Int) {
                if (!view_progress_pie.isTextShowing()) {
                    //view_progress_pie.setShowText(true)
                    view_progress_pie.setShowImage(false)
                }
            }

            override fun onProgressCompleted() {
                if (!view_progress_pie.isImageShowing()) {
                    view_progress_pie.setShowImage(true)
                }
                view_progress_pie.setShowText(false)
               // view_progress_pie.setImageResource(R.drawable.notification_bg)
            }
        })

        seek_progress_pie.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                view_progress_pie.setProgress(p1)
                //view_progress_pie.setText(p1.toString() + "%")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}
