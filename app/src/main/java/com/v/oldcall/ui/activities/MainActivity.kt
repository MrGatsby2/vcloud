package com.v.oldcall.ui.activities

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.v.oldcall.R
import com.v.oldcall.entities.ContactEntity
import com.v.oldcall.ui.adapters.RecentContactsAdapter
import com.v.oldcall.ui.mvps.MainContract
import com.v.oldcall.ui.mvps.MainPresenter
import com.v.oldcall.utils.PinYinStringUtil


class MainActivity : BaseMvpActivity<MainPresenter<MainContract.View>>(),
    MainContract.View, View.OnClickListener {

    private val TAG = "MainActivity"
    private var rvContract: RecyclerView? = null
    private lateinit var adapter: RecentContactsAdapter


    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setNavigationIcon(null)
        val btnAddContract: Button = findViewById(R.id.btn_add_contacts)
        val btnDialler: Button = findViewById(R.id.btn_to_dialler)
        btnAddContract.setOnClickListener(this)
        btnDialler.setOnClickListener(this)

        rvContract = findViewById(R.id.rv_contacts)
        adapter = RecentContactsAdapter(this)
        adapter.setEmptyButtonClickListener(this)

        rvContract!!.let {
            val llm = LinearLayoutManager(this)
            llm.orientation = LinearLayoutManager.VERTICAL
            it.layoutManager = llm
            it.adapter = adapter
        }
    }

    override fun initData() {
        goCheckPermission()
//        mPresenter?.showFrequentContacts()
        test()
    }


    private fun test() {
        val tmp = arrayOf(
            "WANGDA",
            "王大三",
            "姜小强",
            "卫老板",
            "兰丫头",
            "李白",
            "江姐",
            "叇亝",
            "厸厹",
            null
        )
        for (s in tmp) {
            Log.w("vvv", "$s --${PinYinStringUtil.getPinYin(s)}")
            Log.w("vvv", "$s --${PinYinStringUtil.getFirstPinYin(s)}")
            Log.w("vvv", "$s --${PinYinStringUtil.getHanZiAlphaLetter(s)}")
            Log.w("vvv", "$s --${PinYinStringUtil.getPinYinHeadChar(s)}")
            Log.e("vvv", "**************************************")
        }

    }

    override fun showNoContacts(msg: String) {
        dismissLoading()
    }

    override fun updateContacts(contactList: List<ContactEntity?>?) {
        dismissLoading()
    }


    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }

    override fun initPresenter() {
        mPresenter = MainPresenter()
        mPresenter!!.attach(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_dialler -> {
                go2Dialler();
            }
            R.id.btn_add_contacts,
            R.id.lev_btn_empty -> {
                go2AddContactsActivity()
            }
        }
    }

    private fun go2AddContactsActivity() {
        val intent = Intent(this, AddContactsActivity::class.java)
        startActivity(intent)
    }


    private fun go2Dialler() {
        val intent = Intent(Intent.ACTION_DIAL)
        startActivity(intent)
    }

    private fun goCheckPermission() {
        val intent = Intent(this, PermissionCheckActivity::class.java)
        startActivity(intent)
    }


}