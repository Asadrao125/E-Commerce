package com.fictivestudios.imdfitness.webServices

interface Services {

    /* //Signup
     @FormUrlEncoded
     @POST(SIGNUP)
     fun signUp(
         @Field("email") email: String,
         @Field("password") password: String,
         @Field("user_type") userType: String,
         @Field("device_type") deviceType: String,
         @Field("device_token") deviceToken: String
     ): Call<Api_Response<SignUpModel>>

     //OTP verify
     @FormUrlEncoded
     @POST(VERIFICATION)
     fun getOtpVerify(
         @Field("user_id") userId: Int,
         @Field("otp") otp: String
     ): Call<Api_Response<UserModel>>

     //Resnd OTP
     @FormUrlEncoded
     @POST(RESEND_OTP)
     fun resendOTP(
         @Field("user_id") userId: Int

     ): Call<Api_Response<Any>>

     //Resnd OTP
     @FormUrlEncoded
     @POST(FORGOT_RESEND_OTP)
     fun forgetResendOTP(
         @Field("email") email: String
     ): Call<Api_Response<Any>>

     //Complete Profile
     @Multipart
     @POST(UPDATE_PROFILE)
     fun completeProfile(
         @Part("name") name: RequestBody,
         @Part("email") email: RequestBody,
         @Part("country") country: RequestBody,
         @Part("age") age: RequestBody,
         @Part("height") height: RequestBody,
         @Part("weight") weight: RequestBody,
         @Part("experience") experience: RequestBody,
         @Part("expertise") expertise: RequestBody,
         @Part("desired_weight") desired_weight: RequestBody,
         @Part("food_preferences") food_preferences: RequestBody,
         @Part("allergies") allergies: RequestBody,
         @Part("user_type") user_type: RequestBody,
         @Part attachment: MultipartBody.Part?,
     ): Call<Api_Response<UserModel>>

     //Stripe Customer
     @POST(STRIPE_CUSTOMER)
     fun stripeCustomer(
     ): Call<Any>

     //Stripe Token
     @FormUrlEncoded
     @POST(STRIPE_TOKEN)
     fun stripeToken(
         @Field("card[number]") cardNumber: String,
         @Field("card[exp_month]") cardExpMonth: String,
         @Field("card[exp_year]") cardExpYear: String,
         @Field("card[cvc]") cardCvc: String
     ): Call<Any>

     //Stripe Create Card
     @FormUrlEncoded
     @Headers("Content-Type: application/x-www-form-urlencoded")
     @POST(STRIPE_CUSTOMER + "/{cus}" + "/sources")
     fun stripeCreateCard(
         @Path(value = "cus", encoded = true) customerKey: String,
         @Field("source") source: String
     ): Call<Any>

     //Stripe CustomerId
     @FormUrlEncoded
     @POST(STRIPE_CUSTOMERID)
     fun stripeCustomerId(
         @Field("user_id") userId: Int,
         @Field("customer_id") customer_id: String,
         @Field("device_type") deviceType: String,
         @Field("device_token") deviceToken: String

     ): Call<Api_Response<UserModel>>

     //Login
     @FormUrlEncoded
     @POST(LOGIN)
     fun login(
         @Field("email") email: String,
         @Field("password") password: String,
         @Field("device_type") deviceType: String,
         @Field("device_token") deviceToken: String

     ): Call<Api_Response<UserModel>>

     //Forget Email
     @FormUrlEncoded
     @POST(FORGOT_EMAIL)
     fun forgotEmail(
         @Field("email") email: String
     ): Call<Api_Response<Any>>

     //Forget OTP verify
     @FormUrlEncoded
     @POST(FORGOT_OTP)
     fun forgetOTP(
         @Field("email") email: String,
         @Field("otp") otp: String
     ): Call<Api_Response<UserModel>>

     //Reset Password
     @FormUrlEncoded
     @POST(RESET_PASSWORD)
     fun resetPassword(
         @Field("email") email: String,
         @Field("new_password") new_password: String
     ): Call<Api_Response<Any>>

     //Social Login
     @FormUrlEncoded
     @POST(SOCIAL_LOGIN)
     fun socialLogin(
         @Field("access_token") access_token: String,
         @Field("provider") provider: String,
         @Field("device_type") device_type: String,
         @Field("device_token") device_token: String,
     ): Call<Api_Response<UserModel>>

     //Logout
     @POST(LOGOUT)
     fun logout(
     ): Call<Api_Response<Any>>

     //Get Product
     @GET(PRODUCT_LIST)
     fun getProductList(
         @Query("product_name") product_name: String
     ): Call<Api_Response<ArrayList<ProductListModel>>>

     //Add to Cart
     @GET(ADD_TO_CART)
     fun addToCart(
         @Query("product_id") product_id: Int
     ): Call<Api_Response<Any>>

     //Get Product
     @GET(CART_DETAIL)
     fun getCartList(
     ): Call<Api_Response<ArrayList<ProductListModel>>>

     //Delete Cart
     @GET(DELETE_CART)
     fun deleteCart(
         @Query("product_id") product_id: Int
     ): Call<Api_Response<ArrayList<ProductListModel>>>

     //Stripe Create Card
     @GET(STRIPE_CUSTOMER + "/{cus}" + "/sources")
     fun getCardList(
         @Path(value = "cus", encoded = true) customerKey: String,
         @Query("object") card: String
     ): Call<Api_Response<Any>>

     //Stripe SetDefault Card
     @FormUrlEncoded
     @Headers("Content-Type: application/x-www-form-urlencoded")
     @POST(STRIPE_CUSTOMER + "/{cus}")
     fun stripeDefaultCard(
         @Path(value = "cus", encoded = true) customerKey: String,
         @Field("default_source") source: String
     ): Call<Any>

     //Delete Card
     @DELETE(STRIPE_CUSTOMER + "/{cus}" + "/sources" + "/{cardId}")
     fun deleteStripeCard(
         @Path(value = "cus", encoded = true) customerKey: String,
         @Path(value = "cardId", encoded = true) cardId: String,
     ): Call<Any>

     //Checkout
     @FormUrlEncoded

     @POST(CHECKOUT)
     fun checkout(
         @Field("card_id") card_id: String
     ): Call<Api_Response<Any>>

     //Get Order History
     @GET(ORDER_HISTORY)
     fun getOrderList(
         @Query("type") card: String
     ): Call<Api_Response<ArrayList<OrderHistoryModel>>>

     //Get Order History
     @GET(ORDER_HISTORY)
     fun getBookingHistoryList(
         @Query("type") card: String
     ): Call<Api_Response<ArrayList<BookingListModel>>>

     //Get Recipe
     @GET(RECIPE_LIST)
     fun getRecipeList(
     ): Call<Api_Response<ArrayList<RecipeModel>>>

     //Add Recipe
     @Multipart
     @POST(ADD_RECIPE)
     fun addRecipe(
         @Part("name") name: RequestBody,
         @Part("description") description: RequestBody,
         @Part image: MultipartBody.Part?,
         @Part("ingredients") ingredients: RequestBody,
         @Part("cost") cost: RequestBody

     ): Call<Api_Response<UserModel>>

     //Buy Recipe
     @FormUrlEncoded
     @POST(BUY_RECIPE)
     fun buyRecipe(
         @Field("recipe_id") recipe_id: Int,
         @Field("card_id") card_id: String
     ): Call<Api_Response<Any>>

     //Add Video
     @Multipart
     @POST(ADD_VIDEO)
     fun addVideo(
         @Part("name") name: RequestBody,
         @Part attachment: MultipartBody.Part?,
         @Part("cost") cost: RequestBody,
         @Part("description") description: RequestBody

     ): Call<Api_Response<UserModel>>

     //Get Video
     @GET(VIDEO_LIST)
     fun getVideoList(
         @Query("trainer_id") trainer_id: String
     ): Call<Api_Response<ArrayList<VideoListModel>>>

     //Get Video Recipe
     @GET(VIDEO_RECIPE_LIST)
     fun getVideoRecipeList(
     ): Call<Api_Response<VideoRecipeModel>>

     //Buy Video
     @FormUrlEncoded
     @POST(BUY_VIDEO)
     fun buyVideo(
         @Field("video_id") video_id: Int,
         @Field("card_id") card_id: String
     ): Call<Api_Response<Any>>

     //Trainer
     @GET(TRAINERS)
     fun getTrainer(
     ): Call<Api_Response<ArrayList<TrainerModel>>>

     //Student
     @GET(STUDENT_APPOINTMENT_LIST)
     fun getStudent(
     ): Call<Api_Response<ArrayList<StudentModel>>>

     //Get Nutrition
     @GET(VIEW_NUTRITION)
     fun getNutrition(
         @Query("day") day: String
     ): Call<Api_Response<NutiritionModel>>

     //Get Notification
     @GET(NOTIFICATION)
     fun getNotification(
     ): Call<Api_Response<ArrayList<NotificationModel>>>

     //Get Schedule
     @GET(SCHEDULE)
     fun getSchedule(
     ): Call<Api_Response<ArrayList<ScheduleModel>>>

     //Set Schedule
     @FormUrlEncoded
     @POST(SET_SCHEDULE)
     fun setSchedule(
         @Field("day") day: String,
         @Field("start_time") start_time: String,
         @Field("end_time") end_time: String,
         @Field("cost") cost: String,
         @Field("breakin_time") breakin_time: String,
         @Field("breakout_time") breakout_time: String
     ): Call<Api_Response<Any>>

     //Get Schedule
     @GET(SCHEDULE_LIST)
     fun getScheduleList(
         @Query("date") date: String,
         @Query("trainer_id") trainer_id: Int
     ): Call<Api_Response<ArrayList<ScheduleListModel>>>

     //Set Schedule
     @FormUrlEncoded
     @POST(BOOK_APPOINTMENT)
     fun bookAppointment(
         @Field("booking_id") booking_id: Int,
         @Field("card_id") card_id: String
     ): Call<Api_Response<Any>>

     //Get TrainerProfile
     @GET(TRAINER_PROFILE)
     fun getTrainerProfile(
         @Query("trainer_id") trainer_id: Int
     ): Call<Api_Response<TrainerProfileModel>>

     //Get StudentProfile
     @GET(STUDENT_PROFILE)
     fun getStudentProfile(
         @Query("student_id") student_id: Int
     ): Call<Api_Response<TrainerProfileModel>>

     //Get StudentProfile
     @GET(NOTIFICATION_STATUS)
     fun notificationStatus(
         @Query("notification_id") notification_id: Int
     ): Call<Api_Response<ArrayList<NotificationModel>>>

     //Delete Schedule
     @FormUrlEncoded
     @POST(DELETE_SCHEDULE)
     fun deleteSchedule(
         @Field("day") day: String
     ): Call<Api_Response<Any>>

     //Get BookingList (Student)
     @GET(BOOKING_LIST)
     fun bookingList(
     ): Call<Api_Response<ArrayList<BookingListModel>>>

     //Get APPOINTMENT_List (Trainer)
     @GET(APPOINTMENT_LIST)
     fun appointmentList(
     ): Call<Api_Response<ArrayList<AppointmentListModel>>>

     //GUpdate Status
     @GET(UPDATE_STATUS)
     fun updateStatus(
         @Query("booking_id") booking_id: Int
     ): Call<Api_Response<Any>>

     //Get Chat
     @GET(CHAT_MESSAGE)
     fun getChatMessage(
     ): Call<Api_Response<ArrayList<ChatMessageModel>>>

     //Rating trainer_id,rate(1,2,3,4,5),comments
     @FormUrlEncoded
     @POST(RATE_TRAINER)
     fun rating(
         @Field("booking_id") booking_id: String,
         @Field("rating") rating: Float,
         @Field("comments") comments: String
     ): Call<Api_Response<Any>>

     @GET(FAQS)
     fun getFaqs(
     ): Call<Api_Response<ArrayList<FaqsModel>>>*/
}

