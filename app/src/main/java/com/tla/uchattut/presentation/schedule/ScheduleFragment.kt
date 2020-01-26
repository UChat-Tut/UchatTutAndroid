package com.tla.uchattut.presentation.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.model.ScrollMode
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.domain._common.CalendarWrapper
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chat.ChatViewModel
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import com.tla.uchattut.presentation.schedule.adapters.EventsRecyclerAdapter
import com.tla.uchattut.presentation.schedule.calendar_containers.DayBinder
import com.tla.uchattut.presentation.schedule.calendar_containers.MonthHeaderBinder
import com.tla.uchattut.presentation.schedule.dialogs.NotificationSelectorDialog
import com.tla.uchattut.presentation.schedule.dialogs.RepeatingSelectorDialog
import com.tla.uchattut.presentation.schedule.dialogs.color_picker.ColorPickerDialog
import kotlinx.android.synthetic.main.bottom_sheet_add_event.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*
import javax.inject.Inject


class ScheduleFragment : BaseFragment(), EventsRecyclerAdapter.OnEventItemClickListener,
    DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<ScheduleViewModel>(viewModelFactory)
    }

    private lateinit var eventsAdapter: EventsRecyclerAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.scheduleComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendarView()

        eventsAdapter = EventsRecyclerAdapter(
            this
        )
        eventsRecyclerView.adapter = eventsAdapter
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> addEventButton.hide()
                    BottomSheetBehavior.STATE_COLLAPSED -> addEventButton.show()
                }
            }

        })

        addEventButton.setOnClickListener {
            openBottomSheet()
        }

        notificationLayout.setOnClickListener {
            openNotificationSelectorDialog()
        }

        saveButton.setOnClickListener {
            addNewEvent()
        }

        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        startTimeTextView.setOnClickListener {
            showStartTimePickerDialog()
        }

        endTimeTextView.setOnClickListener {
            showEndTimePickerDialog()
        }

        repeatLayout.setOnClickListener {
            openRepeatingSelectorDialog()
        }

        colorLayout.setOnClickListener {
            openColorPickerDialog()
        }

        viewModel.getEventsLiveData().observe(viewLifecycleOwner, Observer {
            eventsAdapter.addEvents(it)
        })

        viewModel.getUpdateCalendarNotifier().observe(viewLifecycleOwner, Observer {
            calendarView.notifyCalendarChanged()
        })

        viewModel.getDateTextLiveData().observe(viewLifecycleOwner, Observer {
            dateTextView.text = it
        })

        viewModel.getStartTimeLiveData().observe(viewLifecycleOwner, Observer {
            startTimeTextView.text = it
        })

        viewModel.getEndTimeLiveData().observe(viewLifecycleOwner, Observer {
            endTimeTextView.text = it
        })

        viewModel.getEventColorLiveData().observe(viewLifecycleOwner, Observer {
            colorView.setCardBackgroundColor(it)
        })

        viewModel.loadEvents()
        val startDayCalendar = CalendarWrapper.getDefaultInstance()
        val endDayCalendar = CalendarWrapper.getDefaultInstance()
        startDayCalendar.add(Calendar.MONTH, -MAX_MONTH_RANGE)
        startDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        endDayCalendar.add(Calendar.MONTH, MAX_MONTH_RANGE + 1)
        endDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        viewModel.loadAllPeriodEvents(MAX_MONTH_RANGE)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (view != null && bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            cancelBottomSheet()
        }
    }

    private fun openNotificationSelectorDialog() {
        NotificationSelectorDialog.show(childFragmentManager)
    }

    private fun openRepeatingSelectorDialog() {
        RepeatingSelectorDialog.show(childFragmentManager)
    }

    private fun openColorPickerDialog() {
        ColorPickerDialog.show(childFragmentManager, ::onColorPicked)
    }

    private fun onColorPicked(color: Int) {
        viewModel.updateEventColor(color)
    }

    private fun showDatePickerDialog() {
        val calendarInstance = Calendar.getInstance()
        val currYear = calendarInstance.get(Calendar.YEAR)
        val currMonth = calendarInstance.get(Calendar.MONTH)
        val currDay = calendarInstance.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context!!, this, currYear, currMonth, currDay
        )

        datePickerDialog.show()
    }

    private val startTimeCallback = OnTimeSetListener { _, hour, minute ->
        viewModel.updateStartTime(hour, minute)
    }

    private fun showStartTimePickerDialog() {
        val lastChosenStartTime = viewModel.getChosenStartCalendarTime()
        val hours = lastChosenStartTime.get(Calendar.HOUR_OF_DAY)
        val minutes = lastChosenStartTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, startTimeCallback, hours, minutes, true)
        timePickerDialog.show()
    }

    private val endTimeCallback = OnTimeSetListener { _, hour, minute ->
        viewModel.updateEndTime(hour, minute)
    }

    private fun showEndTimePickerDialog() {
        val lastChosenEndTime = viewModel.getChosenEndCalendarTime()
        val hours = lastChosenEndTime.get(Calendar.HOUR_OF_DAY)
        val minutes = lastChosenEndTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, endTimeCallback, hours, minutes, true)
        timePickerDialog.show()
    }

    private fun setupCalendarView() {
        calendarView.dayBinder = DayBinder(viewModel)
        calendarView.monthHeaderBinder = MonthHeaderBinder(calendarView)

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(MAX_MONTH_RANGE.toLong())
        val lastMonth = currentMonth.plusMonths(MAX_MONTH_RANGE.toLong())
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        calendarView.run {
            dayWidth = screenWidth / DAYS_IN_WEEK
            dayHeight = resources.getDimension(R.dimen.calendar_view_day_height).toInt()
            inDateStyle = InDateStyle.ALL_MONTHS
            outDateStyle = OutDateStyle.END_OF_GRID
            scrollMode = ScrollMode.PAGED
            orientation = RecyclerView.HORIZONTAL
            hasBoundaries = true
            maxRowCount = 6
            setup(firstMonth, lastMonth, firstDayOfWeek)
            scrollToMonth(currentMonth)
        }
    }

    private fun openBottomSheet() {
        viewModel.initBottomSheetData()
        addEventButton.hide()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onEventItemClick(id: Int) {
        toast("Элемент с id: $id")
    }

    private fun addNewEvent() {
        val newEvent = buildNewEvent()
        viewModel.addEvent(newEvent)
        cancelBottomSheet()
    }

    private fun buildNewEvent(): EventPresentationModel = EventPresentationModel(
        title = titleEditText.text.toString(),
        date = viewModel.getChosenCalendarDay().time,
        startCalendarTime = viewModel.getChosenStartCalendarTime(),
        endCalendarTime = viewModel.getChosenEndCalendarTime(),
        color = viewModel.getChosenColor()
    )

    private fun cancelBottomSheet() {
        titleEditText.text.clear()
        addEventButton.show()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setNewEventDate(year, month, dayOfMonth)
    }

    companion object {
        private const val MAX_MONTH_RANGE = 10
        private const val DAYS_IN_WEEK = 7
    }
}