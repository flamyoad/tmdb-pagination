package com.flamyoad.tmdb.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.flamyoad.tmdb.R
import com.flamyoad.tmdb.model.ProductionCompany
import com.google.android.material.textview.MaterialTextView

class MovieProdCompaniesAdapter :
    RecyclerView.Adapter<MovieProdCompaniesAdapter.CompanyViewHolder>() {
    private var companyList: List<ProductionCompany> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_details_company_list_item, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companyList[position])
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    fun setList(companyList: List<ProductionCompany>) {
        this.companyList = companyList
        notifyDataSetChanged()
    }

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgLogo: AppCompatImageView = itemView.findViewById(R.id.imgLogo)
        private val txtTitle: MaterialTextView = itemView.findViewById(R.id.txtTitle)
        private val txtOriginCountry: MaterialTextView = itemView.findViewById(R.id.txtOriginCountry)

        fun bind(company: ProductionCompany) {
            val loadingIndicator = CircularProgressDrawable(itemView.context).apply {
                setColorSchemeColors(Color.BLACK)
                centerRadius = 50f
                strokeWidth = 5f
            }

            loadingIndicator.start()
            Glide.with(itemView.context)
                .load(company.getLogoUrl())
                .placeholder(loadingIndicator)
                .into(imgLogo)

            txtTitle.text = company.name
            txtOriginCountry.text = company.originCountry
        }
    }
}