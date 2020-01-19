package com.tla.uchattut.presentation.schedule.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.domain._common.CalendarWrapper
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import com.tla.uchattut.presentation.schedule.view.adapters.EventsRecyclerAdapter
import com.tla.uchattut.presentation.schedule.view.calendar_containers.DayViewContainer
import com.tla.uchattut.presentation.schedule.view.calendar_containers.MonthHeaderViewContainer
import com.tla.uchattut.presentation.schedule.view.dialogs.color_picker.ColorPickerDialog
import com.tla.uchattut.presentation.schedule.view.dialogs.NotificationSelectorDialog
import com.tla.uchattut.presentation.schedule.view.dialogs.RepeatingSelectorDialog
import com.tla.uchattut.presentation.schedule.view_model.ScheduleViewModel
import kotlinx.android.synthetic.main.bottom_sheet_add_event.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.threeten.bp.YearMonth
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.WeekFields
import java.util.*
import javax.inject.Inject


class ScheduleFragment : Fragment(), EventsRecyclerAdapter.OnEventItemClickListener,
    DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModel: ScheduleViewModel
    private lateinit var eventsAdapter: EventsRecyclerAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var months: Array<String>
    private var lastChoosedDayView: View? = null

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

        eventsAdapter =
            EventsRecyclerAdapter(
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
        startDayCalendar.add(Calendar.MONTH, -10)
        startDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        endDayCalendar.add(Calendar.MONTH, 11)
        endDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        viewModel.loadAllPeriodEvents(10)
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
        months = resources.getStringArray(R.array.months)

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) =
                DayViewContainer(
                    view
                )

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.calendarDayTextView.text = day.date.dayOfMonth.toString()
                paintDatCard(container, day)
                provideEvents(container, day)
            }

            private fun paintDatCard(container: DayViewContainer, day: CalendarDay) {
                when {
                    day.date.isEqual(ZonedDateTime.now().chronology.dateNow()) -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.white))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_current)

                        container.calendarDayView.setOnClickListener {
                            loadEvents(day)
                            lastChoosedDayView?.isActivated = false
                            container.calendarDayView.isActivated = true
                            lastChoosedDayView = container.calendarDayView
                        }
                    }
                    day.owner == DayOwner.THIS_MONTH -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.black))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_in_month)
                        container.calendarDayView.setOnClickListener {
                            loadEvents(day)
                            lastChoosedDayView?.isActivated = false
                            container.calendarDayView.isActivated = true
                            lastChoosedDayView = container.calendarDayView
                        }
                    }
                    else -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(R.color.silverChariot))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_out_month)
                    }
                }
            }

            private fun loadEvents(day: CalendarDay) {
                val year = day.date.year
                val month = day.date.monthValue - 1
                val dayOfMonth = day.date.dayOfMonth
                viewModel.loadEvents(year, month, dayOfMonth)
            }

            private fun provideEvents(container: DayViewContainer, day: CalendarDay) {
                val dayEvents = viewModel.getEventsForCalendarDay(day)
                val lineMaxCount = container.lineViews.size
                if (dayEvents != null) {
                    for (i in dayEvents.indices) {
                        if (i == lineMaxCount) {
                            container.lineViews[lineMaxCount - 1].setBackgroundColor(
                                Color.BLACK
                            )
                            break
                        }
                        container.lineViews[i].visibility = View.VISIBLE
                        container.lineViews[i].setBackgroundColor(dayEvents[i].color)
                    }
                }
            }
        }

        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthHeaderViewContainer> {
                override fun create(view: View): MonthHeaderViewContainer =
                    MonthHeaderViewContainer(
                        view
                    )

                @SuppressLint("SetTextI18n")
                override fun bind(container: MonthHeaderViewContainer, month: CalendarMonth) {
                    container.calendarMonthText.text = "${months[month.month - 1]}, ${month.year}"

                    container.backButton.setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.minusMonths(1))
                    }

                    container.forwardButton.setOnClickListener {
                        calendarView.smoothScrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }
            }


        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        calendarView.run {
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
        const val TAG = "ScheduleFragment"
    }
}