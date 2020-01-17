package com.tla.uchattut.presentation.schedule.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import com.tla.uchattut.presentation.schedule.view_model.ScheduleViewModel
import kotlinx.android.synthetic.main.bottom_sheet_add_event.*
import kotlinx.android.synthetic.main.fragment_chat.*
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

        eventsAdapter = EventsRecyclerAdapter(this)
        eventsRecyclerView.adapter = eventsAdapter
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

        })

        val calendarInstance = Calendar.getInstance()
        val currYear = calendarInstance.get(Calendar.YEAR)
        val currMonth = calendarInstance.get(Calendar.MONTH)
        val currDay = calendarInstance.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context!!, this, currYear, currMonth, currDay
        )

        saveButton.setOnClickListener { addNewEvent() }
        dateTextView.setOnClickListener { datePickerDialog.show() }
        startTimeTextView.setOnClickListener { }
        endTimeTextView.setOnClickListener { }
        repeatLayout.setOnClickListener { }
        colorLayout.setOnClickListener { }

        viewModel.getEventsLiveData().observe(viewLifecycleOwner, Observer {
            eventsAdapter.addEvents(it)
        })

        viewModel.getUpdateCalendarNotifier().observe(viewLifecycleOwner, Observer {
            calendarView.notifyCalendarChanged()
        })

        viewModel.loadEvents()
        val startDayCalendar = Calendar.getInstance()
        val endDayCalendar = Calendar.getInstance()
        startDayCalendar.add(Calendar.MONTH, -10)
        startDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        endDayCalendar.add(Calendar.MONTH, 11)
        endDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

        viewModel.loadAllPeriodEvents(10)
    }

    private fun setupCalendarView() {
        months = resources.getStringArray(R.array.months)

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

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

                        container.calendarDayView.setOnClickListener { loadEvents(day) }
                    }
                    day.owner == DayOwner.THIS_MONTH -> {
                        container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.black))
                        container.calendarDayView.background =
                            resources.getDrawable(R.drawable.background_day_calendar_in_month)
                        container.calendarDayView.setOnClickListener { loadEvents(day) }
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
                    }
                }
            }
        }

        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthHeaderViewContainer> {
                override fun create(view: View): MonthHeaderViewContainer =
                    MonthHeaderViewContainer(view)

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

    override fun onNewItemClick() {
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
        date = viewModel.getNewEventDate().time,
        startTimestamp = 0,
        endTimestamp = 0
    )

    private fun cancelBottomSheet() {
        titleEditText.text.clear()

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setNewEventDate(year, month, dayOfMonth)
        dateTextView.text = viewModel.formatCalendarDate(viewModel.getNewEventDate())
    }
}