package com.example.mimoapp.showSingleLesson

import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.mimoapp.R
import com.example.mimoapp.api.model.Content
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
import android.text.Editable
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mimoapp.api.model.Lesson
import com.example.mimoapp.databinding.FragmentSingleLessonBinding
import com.example.mimoapp.roomDB.LessonCompletedModel
import com.example.mimoapp.util.blockingClickListener
import com.example.mimoapp.util.showMessageToUser
import java.sql.Time


@AndroidEntryPoint
class SingleLessonFragment : Fragment(R.layout.fragment_single_lesson) {

    private lateinit var textView: TextView
    private lateinit var textViewsArrayList: MutableList<TextViewList>
    private var sumOfContentTextCharacters = 0
    private var isInputUserRequired = false
    private var textViewId = 0
    private val navArgs by navArgs<SingleLessonFragmentArgs>()
    private var userinputString = ""
    private lateinit var lessonDetails: Lesson
    private lateinit var binding: FragmentSingleLessonBinding

    @Inject
    lateinit var viewModelFactory: SingleLessonFragmentViewModelFactory

    val viewModel: SingleLessonFragmentViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startTime = Time(System.currentTimeMillis())

        lessonDetails = navArgs.singleLessonDetailsArgs

        //binding the layout file to access its views
        binding = FragmentSingleLessonBinding.bind(view)

        textViewsArrayList = mutableListOf()

        if (lessonDetails.input != null) {
            isInputUserRequired = true

            addEditTextAndTextViewsToTextViewArrayList()

        } else {

            //No Input hence all views will be TextViews
            addTextViewsOnlyToTextViewArrayList()

        }

        //Render the views on the screen for the user to interact with the app
        //the rendered views are TextViews and EditText
        renderViewsToScreen()


        enableOrDisableRunBtnUsingUserInput()


        handleRunButtonClicks(startTime)


        checkIfLessonIsDoneOrNotDone()

    }

    private fun checkIfLessonIsDoneOrNotDone() {
        viewModel.retrieveSavedLessonDetails(lessonDetails.id)
            .observe(viewLifecycleOwner, {

                if (it != null) {
                    binding.lessonStatusTextview.text =
                        "Lesson Status: DONE\n Lesson Started At: ${it.startTime}\n" +
                                "Lesson Finished At: ${it.endTime}"
                    binding.runButton.isEnabled = false
                } else {

                    binding.lessonStatusTextview.text = "Lesson Status: NOT DONE"
                }


            })
    }

    private fun handleRunButtonClicks(startTime: Time) {
        binding.runButton.blockingClickListener {
            val endTime = Time(System.currentTimeMillis())
            viewModel.insertCompletedLesson(
                lessonCompleted = LessonCompletedModel(
                    lessonDetails.id,
                    startTime,
                    endTime
                )
            )

                showMessageToUser(requireContext(),getString(R.string.lesson_solved_message) )
            val action: NavDirections =
                SingleLessonFragmentDirections.actionSingleLessonFragmentToLessonListFragment()
            findNavController().navigate(action)

        }
    }

    private fun addTextViewsOnlyToTextViewArrayList() {
        for (lessonContent in lessonDetails.content) {

            createTextViewObject(lessonContent)
            textViewsArrayList.add(TextViewList(textView, false, "textview$textViewId"))
            textViewId++

        }

    }

    private fun addEditTextAndTextViewsToTextViewArrayList() {
        if (lessonDetails.input!!.startIndex == 0) {
            // if the starting index is zero. The EditText will be the first view to
            // be displayed. Hence we add it viewsArrayList as the first element.
            textViewsArrayList.add(0, TextViewList(EditText(activity), true, "editTextElement"))
        }
        for (lessonContent in lessonDetails.content) {
            //EditText will not be the first view on the screen

            removeWhiteSpacesFromTextAndAddTextCharacters(lessonContent)

            createTextViewObject(lessonContent)
            //add created TextView objects to the textViewsArrayList
            textViewsArrayList.add(TextViewList(textView, false, "textview$textViewId"))
            textViewId++

            if (sumOfContentTextCharacters + 1 == lessonDetails.input!!.startIndex) {

                textViewsArrayList.add(TextViewList(EditText(activity), true, "editTextElement"))

            }
        }
    }


    private fun enableOrDisableRunBtnUsingUserInput() {
        if (isInputUserRequired) {
            binding.runButton.isEnabled = false
            val userInputEditTxt = textViewsArrayList.filter {
                it.textViewId == "editTextElement"
            }

            userInputEditTxt[0].textView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    userinputString = removeWhiteSpacesFromString(userinputString)
                    val typedInput = removeWhiteSpacesFromString(s.toString())
                    binding.runButton.isEnabled = userinputString == typedInput

                }
            })


        } else {
            binding.runButton.isEnabled = true
        }
    }

    private fun removeWhiteSpacesFromTextAndAddTextCharacters(lessonContent: Content) {


        sumOfContentTextCharacters += removeWhiteSpacesFromString(lessonContent.text).length

    }

    private fun removeWhiteSpacesFromString(text: String): String {

        return text.replace("\\s".toRegex(), "")
    }


    private fun createTextViewObject(lessonContent: Content) {
        textView = TextView(activity)
        textView.textSize = 24f
        textView.setTextColor(Color.parseColor(lessonContent.color))
        textView.text = lessonContent.text
    }

    private fun renderViewsToScreen() {
        for (i in 0 until textViewsArrayList.size) {

            if (textViewsArrayList[i].isEditText) {

                try {

                    textViewsArrayList[i + 1].textView.visibility = View.GONE
                    userinputString = textViewsArrayList[i + 1].textView.text.toString()

                } catch (e: Exception) {
                    textViewsArrayList[i - 1].textView.visibility = View.GONE
                    userinputString = textViewsArrayList[i - 1].textView.toString()
                }

            }

            binding.displayTextViewsParent.addView(textViewsArrayList[i].textView)

        }
    }

}