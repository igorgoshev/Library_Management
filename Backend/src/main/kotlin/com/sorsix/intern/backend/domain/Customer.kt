    package com.sorsix.intern.backend.domain

    import jakarta.persistence.Entity
    import jakarta.persistence.OneToMany
    import jakarta.persistence.OneToOne
    import jakarta.persistence.PrimaryKeyJoinColumn

    @Entity
    @PrimaryKeyJoinColumn
    data class Customer(
        @OneToOne(mappedBy = "customer")
        val wishList: WishList,

        @OneToMany(mappedBy = "customer")
        val trades: List<Trade>,

        @OneToMany(mappedBy = "customer")
        val customerBooks: List<CustomerBook>,

        @OneToMany(mappedBy = "customer")
        val reviews: List<Review>,

        @OneToMany(mappedBy = "customer")
        val subscriptions: List<Subscription>,

        @OneToMany(mappedBy = "customer")
        val reserveBooks: List<ReserveBook>,

        @OneToMany(mappedBy = "customer")
        val borrowedBooks: List<BorrowBook>

    ): User() {
    }