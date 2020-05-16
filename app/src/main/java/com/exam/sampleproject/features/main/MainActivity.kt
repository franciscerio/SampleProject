package com.exam.sampleproject.features.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exam.domain.poko.Trip
import com.exam.sampleproject.R
import com.exam.sampleproject.base.BaseViewModelActivity
import com.exam.sampleproject.databinding.ActivityMainBinding
import com.exam.sampleproject.ext.*
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        const val DRIVER_KEY = "driver"
        const val DRIVER_TRIP_KEY = "trip"
    }

    private val listAdapter: MainAdapter by lazy {
        MainAdapter(mutableListOf())
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViews()
        setupViewModel()
        setupRecyclerView()
    }

    // Setup view model
    private fun setupViewModel() {
        viewModel.state
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = { state ->
                    when (state) {

                        is MainState.DriverAlreadyExist -> {
                            toast(state.name)
                        }

                        is MainState.AddItem -> {
                            listAdapter.addItem(state.item)
                            binding.inputName.setText("")
                        }

                        is MainState.ExpectedOutput -> {
                            val builder = StringBuilder()

                            state.items.forEachIndexed { _, output ->

                                builder.append(output.name).append(":").append(" ")
                                    .append(output.miles).append(" ").append("miles").append(" ")
                                    .append("@").append(" ")
                                    .append(output.mph).append(" ").append("mph").appendln()
                            }

                            binding.expectedOutput.text = builder.toString()
                        }

                        is MainState.EmptyList -> {
                            toast(getString(R.string.list_must_not_empty))
                        }

                        is MainState.InvalidTime -> {
                            binding.inputName.setSelection(state.position)
                        }

                        is MainState.StartTimeGreaterThanStopTime -> {
                            toast(getString(R.string.start_time_greater_than_stop_time))
                        }

                        is MainState.UpdateList -> {
                            listAdapter.updateList(state.items)
                        }

                    }
                },
                onError = {
                    Timber.e("Error $it")
                }
            )
            .apply {
                disposables.add(this)
            }
    }

    // setup recycler view
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding
            .tripList
            .apply {
                layoutManager = linearLayoutManager
                adapter = listAdapter
            }
    }

    // observe views using rxbindings
    private fun observeViews() {
        binding.inputName.capitalizeFirstLetter().addTo(disposables)

        val observeInputName = binding.inputName.textChangeEvents()
            .skipInitialValue()
            .map { it.text }
            .map {
                it.isNotEmpty() && it.contains(DRIVER_KEY, ignoreCase = true)
                        || it.contains(DRIVER_TRIP_KEY, ignoreCase = true)
            }

        observeInputName
            .debounce(250, TimeUnit.MILLISECONDS)
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = {
                    if (it) {
                        binding.btnGo.enabledWithAlpha()
                    } else {
                        binding.btnGo.disabledWithAlpha()
                    }
                },
                onError = {
                    Timber.e("Error $it")
                }
            ).addTo(disposables)

        // toggle btnCalculate
        viewModel.listOfDrivers.observe(this, Observer {
            if (it.size > 0) {
                binding.btnCalculate.enabledWithAlpha()
            } else {
                binding.btnCalculate.disabledWithAlpha()
            }
        })

        // guard click listener using ninjaTap
        binding.btnCalculate.ninjaTap {
            viewModel.calculateItems()
        }

        binding
            .btnGo.ninjaTap {
            val inputName = binding.inputName.text.toString()

            val splitInput = inputName.split(" ")
            val type = splitInput[0]


            // Check if user input's a type (DRIVER OR TRIP) only
            if (splitInput.size <= 1) {
                toast(getString(R.string.please_add_driver_name))
                return@ninjaTap
            }

            val name = splitInput[1]

            // Driver flow
            if (type.contains(DRIVER_KEY, ignoreCase = true)) {
                viewModel.saveOrIgnoreDriverData(name)
            }
            // Driver trip flow
            else {
                val startTime = splitInput[2]
                val stopTime = splitInput[3]
                val distance = splitInput[4].toDoubleOrNull() ?: 0.0

                val formatter = DateTimeFormatter.ofPattern("HH:mm")

                if (!formatter.validate(startTime)) {
                    val cursorPosition = type.length + name.length + startTime.length + 3
                    viewModel.invalidTimeFormat(cursorPosition)
                    return@ninjaTap
                }

                if (!formatter.validate(stopTime)) {
                    val cursorPosition =
                        type.length + name.length + startTime.length + stopTime.length + 4
                    viewModel.invalidTimeFormat(cursorPosition)
                    return@ninjaTap
                }

                val startDateTime = LocalTime.parse(startTime, formatter)
                val stopDateTime = LocalTime.parse(stopTime, formatter)

                if (stopDateTime > startDateTime) {
                    viewModel.saveOrIgnoreDriverTripData(
                        Trip(
                            name,
                            startTime,
                            stopTime,
                            distance
                        )
                    )
                } else {
                    viewModel.invalidStartTime()
                }

            }

        }.addTo(disposables)

    }
}