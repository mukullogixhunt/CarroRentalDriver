package com.carro.chauffeur.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingListModel {

    @SerializedName("m_bking_id")
    @Expose
    private String mBkingId;
    @SerializedName("m_bking_tc")
    @Expose
    private String mBkingTc;
    @SerializedName("m_booking_id")
    @Expose
    private String mBookingId;
    @SerializedName("m_bking_vendor")
    @Expose
    private String mBkingVendor;
    @SerializedName("m_bking_user")
    @Expose
    private String mBkingUser;
    @SerializedName("m_bking_username")
    @Expose
    private String mBkingUsername;
    @SerializedName("m_bking_useremail")
    @Expose
    private String mBkingUseremail;
    @SerializedName("m_bking_usermobile")
    @Expose
    private String mBkingUsermobile;
    @SerializedName("m_bking_type")
    @Expose
    private String mBkingType;
    @SerializedName("m_bking_type_cat")
    @Expose
    private String mBkingTypeCat;
    @SerializedName("m_bking_road_type")
    @Expose
    private String mBkingRoadType;
    @SerializedName("m_bking_hour")
    @Expose
    private String mBkingHour;
    @SerializedName("m_bking_inside_outside_state")
    @Expose
    private String mBkingInsideOutsideState;
    @SerializedName("m_bking_inside_outside_state_amt")
    @Expose
    private String mBkingInsideOutsideStateAmt;
    @SerializedName("m_bking_pick_drop_both")
    @Expose
    private String mBkingPickDropBoth;
    @SerializedName("m_bking_pick_drop_both_amt")
    @Expose
    private String mBkingPickDropBothAmt;
    @SerializedName("m_bking_branch")
    @Expose
    private String mBkingBranch;
    @SerializedName("m_bking_package")
    @Expose
    private String mBkingPackage;
    @SerializedName("m_bking_subs")
    @Expose
    private String mBkingSubs;
    @SerializedName("m_bking_service_type")
    @Expose
    private String mBkingServiceType;
    @SerializedName("m_bking_from")
    @Expose
    private String mBkingFrom;
    @SerializedName("m_bking_to")
    @Expose
    private String mBkingTo;
    @SerializedName("m_bking_pickup_address")
    @Expose
    private String mBkingPickupAddress;
    @SerializedName("m_bking_drop_address")
    @Expose
    private String mBkingDropAddress;
    @SerializedName("m_bking_pickup")
    @Expose
    private String mBkingPickup;
    @SerializedName("m_bking_pickup_at")
    @Expose
    private String mBkingPickupAt;
    @SerializedName("m_bking_return")
    @Expose
    private String mBkingReturn;
    @SerializedName("m_bking_return_at")
    @Expose
    private String mBkingReturnAt;
    @SerializedName("m_bking_flight")
    @Expose
    private String mBkingFlight;
    @SerializedName("m_bking_car_type")
    @Expose
    private String mBkingCarType;
    @SerializedName("m_bking_car")
    @Expose
    private String mBkingCar;
    @SerializedName("m_bking_bus")
    @Expose
    private String mBkingBus;
    @SerializedName("m_bking_driver")
    @Expose
    private String mBkingDriver;
    @SerializedName("m_bking_fastag")
    @Expose
    private String mBkingFastag;
    @SerializedName("m_bking_hour_range")
    @Expose
    private String mBkingHourRange;
    @SerializedName("m_bking_km")
    @Expose
    private String mBkingKm;
    @SerializedName("m_bking_price")
    @Expose
    private String mBkingPrice;
    @SerializedName("m_bking_couponid")
    @Expose
    private String mBkingCouponid;
    @SerializedName("m_bking_dis")
    @Expose
    private String mBkingDis;
    @SerializedName("m_bking_damt")
    @Expose
    private String mBkingDamt;
    @SerializedName("m_bking_total")
    @Expose
    private String mBkingTotal;
    @SerializedName("m_bking_paid_amt")
    @Expose
    private String mBkingPaidAmt;
    @SerializedName("m_bking_remain_amt")
    @Expose
    private String mBkingRemainAmt;
    @SerializedName("m_bking_paymode")
    @Expose
    private String mBkingPaymode;
    @SerializedName("m_bking_pay_status")
    @Expose
    private String mBkingPayStatus;
    @SerializedName("m_bking_status")
    @Expose
    private String mBkingStatus;
    @SerializedName("m_bking_trans_id")
    @Expose
    private String mBkingTransId;
    @SerializedName("m_bking_addedon")
    @Expose
    private String mBkingAddedon;
    @SerializedName("m_bking_updatedon")
    @Expose
    private String mBkingUpdatedon;
    @SerializedName("m_vendor_id")
    @Expose
    private String mVendorId;
    @SerializedName("m_vendor_name")
    @Expose
    private String mVendorName;
    @SerializedName("m_vendor_mobile")
    @Expose
    private String mVendorMobile;
    @SerializedName("m_vendor_email")
    @Expose
    private String mVendorEmail;
    @SerializedName("m_branch_id")
    @Expose
    private String mBranchId;
    @SerializedName("m_branch_title")
    @Expose
    private String mBranchTitle;
    @SerializedName("m_ctype_id")
    @Expose
    private String mCtypeId;
    @SerializedName("m_ctype_title")
    @Expose
    private String mCtypeTitle;
    @SerializedName("m_ctype_img")
    @Expose
    private String mCtypeImg;
    @SerializedName("m_ctype_seat")
    @Expose
    private String mCtypeSeat;
    @SerializedName("m_ctype_luggage")
    @Expose
    private String mCtypeLuggage;
    @SerializedName("m_ctype_AC")
    @Expose
    private String mCtypeAC;
    @SerializedName("m_ctype_fuel")
    @Expose
    private String mCtypeFuel;
    @SerializedName("m_ctype_number")
    @Expose
    private String mCtypeNumber;
    @SerializedName("m_ctype_km")
    @Expose
    private String mCtypeKm;
    @SerializedName("m_ctype_price")
    @Expose
    private String mCtypePrice;
    @SerializedName("m_ctype_drivetype")
    @Expose
    private String mCtypeDrivetype;
    @SerializedName("m_ctype_servtype")
    @Expose
    private String mCtypeServtype;
    @SerializedName("m_ctype_servtype_cat")
    @Expose
    private String mCtypeServtypeCat;
    @SerializedName("m_ctype_branch")
    @Expose
    private String mCtypeBranch;
    @SerializedName("m_ctype_inclusion")
    @Expose
    private String mCtypeInclusion;
    @SerializedName("m_ctype_exclusion")
    @Expose
    private String mCtypeExclusion;
    @SerializedName("m_ctype_tc")
    @Expose
    private String mCtypeTc;
    @SerializedName("m_ctype_reg_certi")
    @Expose
    private String mCtypeRegCerti;
    @SerializedName("m_ctype_iss_certi")
    @Expose
    private String mCtypeIssCerti;
    @SerializedName("m_ctype_permit")
    @Expose
    private String mCtypePermit;
    @SerializedName("m_ctype_ownercar_img")
    @Expose
    private String mCtypeOwnercarImg;
    @SerializedName("m_ctype_order")
    @Expose
    private String mCtypeOrder;
    @SerializedName("m_ctype_status")
    @Expose
    private String mCtypeStatus;
    @SerializedName("m_ctype_addedby")
    @Expose
    private String mCtypeAddedby;
    @SerializedName("m_ctype_addedon")
    @Expose
    private String mCtypeAddedon;
    @SerializedName("m_ctype_updatedon")
    @Expose
    private String mCtypeUpdatedon;
    @SerializedName("m_cust_id")
    @Expose
    private String mCustId;
    @SerializedName("m_cust_name")
    @Expose
    private String mCustName;
    @SerializedName("m_cust_mobile")
    @Expose
    private String mCustMobile;
    @SerializedName("m_cust_email")
    @Expose
    private String mCustEmail;

    // Existing fields from your old model that were not in the JSON but might be needed
    // If not needed, you can remove them.
    @SerializedName("m_bus_title")
    @Expose
    private String mBusTitle;
    @SerializedName("m_car_id")
    @Expose
    private String mCarId;
    @SerializedName("m_car_name")
    @Expose
    private String mCarName;
    @SerializedName("m_car_number")
    @Expose
    private String mCarNumber;
    @SerializedName("m_car_type")
    @Expose
    private String mCarType;
    @SerializedName("m_car_brand")
    @Expose
    private String mCarBrand;
    @SerializedName("m_car_model")
    @Expose
    private String mCarModel;
    @SerializedName("m_car_vendor")
    @Expose
    private String mCarVendor;
    @SerializedName("m_car_fuel")
    @Expose
    private String mCarFuel;
    @SerializedName("m_car_img")
    @Expose
    private String mCarImg;
    @SerializedName("m_car_seat")
    @Expose
    private String mCarSeat;
    @SerializedName("m_car_reg_certi")
    @Expose
    private String mCarRegCerti;
    @SerializedName("m_car_iss_certi")
    @Expose
    private String mCarIssCerti;
    @SerializedName("m_car_permit")
    @Expose
    private String mCarPermit;
    @SerializedName("m_car_ownercar_img")
    @Expose
    private String mCarOwnercarImg;
    @SerializedName("m_car_status")
    @Expose
    private String mCarStatus;
    @SerializedName("m_car_addedon")
    @Expose
    private String mCarAddedon;
    @SerializedName("m_car_updatedon")
    @Expose
    private String mCarUpdatedon;

    // --- GETTERS AND SETTERS FOR ALL FIELDS ---

    public String getmBkingId() {
        return mBkingId;
    }

    public void setmBkingId(String mBkingId) {
        this.mBkingId = mBkingId;
    }

    public String getmBkingTc() {
        return mBkingTc;
    }

    public void setmBkingTc(String mBkingTc) {
        this.mBkingTc = mBkingTc;
    }

    public String getmBookingId() {
        return mBookingId;
    }

    public void setmBookingId(String mBookingId) {
        this.mBookingId = mBookingId;
    }

    public String getmBkingVendor() {
        return mBkingVendor;
    }

    public void setmBkingVendor(String mBkingVendor) {
        this.mBkingVendor = mBkingVendor;
    }

    public String getmBkingUser() {
        return mBkingUser;
    }

    public void setmBkingUser(String mBkingUser) {
        this.mBkingUser = mBkingUser;
    }

    public String getmBkingUsername() {
        return mBkingUsername;
    }

    public void setmBkingUsername(String mBkingUsername) {
        this.mBkingUsername = mBkingUsername;
    }

    public String getmBkingUseremail() {
        return mBkingUseremail;
    }

    public void setmBkingUseremail(String mBkingUseremail) {
        this.mBkingUseremail = mBkingUseremail;
    }

    public String getmBkingUsermobile() {
        return mBkingUsermobile;
    }

    public void setmBkingUsermobile(String mBkingUsermobile) {
        this.mBkingUsermobile = mBkingUsermobile;
    }

    public String getmBkingType() {
        return mBkingType;
    }

    public void setmBkingType(String mBkingType) {
        this.mBkingType = mBkingType;
    }

    public String getmBkingTypeCat() {
        return mBkingTypeCat;
    }

    public void setmBkingTypeCat(String mBkingTypeCat) {
        this.mBkingTypeCat = mBkingTypeCat;
    }

    public String getmBkingRoadType() {
        return mBkingRoadType;
    }

    public void setmBkingRoadType(String mBkingRoadType) {
        this.mBkingRoadType = mBkingRoadType;
    }

    public String getmBkingHour() {
        return mBkingHour;
    }

    public void setmBkingHour(String mBkingHour) {
        this.mBkingHour = mBkingHour;
    }

    public String getmBkingInsideOutsideState() {
        return mBkingInsideOutsideState;
    }

    public void setmBkingInsideOutsideState(String mBkingInsideOutsideState) {
        this.mBkingInsideOutsideState = mBkingInsideOutsideState;
    }

    public String getmBkingInsideOutsideStateAmt() {
        return mBkingInsideOutsideStateAmt;
    }

    public void setmBkingInsideOutsideStateAmt(String mBkingInsideOutsideStateAmt) {
        this.mBkingInsideOutsideStateAmt = mBkingInsideOutsideStateAmt;
    }

    public String getmBkingPickDropBoth() {
        return mBkingPickDropBoth;
    }

    public void setmBkingPickDropBoth(String mBkingPickDropBoth) {
        this.mBkingPickDropBoth = mBkingPickDropBoth;
    }

    public String getmBkingPickDropBothAmt() {
        return mBkingPickDropBothAmt;
    }

    public void setmBkingPickDropBothAmt(String mBkingPickDropBothAmt) {
        this.mBkingPickDropBothAmt = mBkingPickDropBothAmt;
    }

    public String getmBkingBranch() {
        return mBkingBranch;
    }

    public void setmBkingBranch(String mBkingBranch) {
        this.mBkingBranch = mBkingBranch;
    }

    public String getmBkingPackage() {
        return mBkingPackage;
    }

    public void setmBkingPackage(String mBkingPackage) {
        this.mBkingPackage = mBkingPackage;
    }

    public String getmBkingSubs() {
        return mBkingSubs;
    }

    public void setmBkingSubs(String mBkingSubs) {
        this.mBkingSubs = mBkingSubs;
    }

    public String getmBkingServiceType() {
        return mBkingServiceType;
    }

    public void setmBkingServiceType(String mBkingServiceType) {
        this.mBkingServiceType = mBkingServiceType;
    }

    public String getmBkingFrom() {
        return mBkingFrom;
    }

    public void setmBkingFrom(String mBkingFrom) {
        this.mBkingFrom = mBkingFrom;
    }

    public String getmBkingTo() {
        return mBkingTo;
    }

    public void setmBkingTo(String mBkingTo) {
        this.mBkingTo = mBkingTo;
    }

    public String getmBkingPickupAddress() {
        return mBkingPickupAddress;
    }

    public void setmBkingPickupAddress(String mBkingPickupAddress) {
        this.mBkingPickupAddress = mBkingPickupAddress;
    }

    public String getmBkingDropAddress() {
        return mBkingDropAddress;
    }

    public void setmBkingDropAddress(String mBkingDropAddress) {
        this.mBkingDropAddress = mBkingDropAddress;
    }

    public String getmBkingPickup() {
        return mBkingPickup;
    }

    public void setmBkingPickup(String mBkingPickup) {
        this.mBkingPickup = mBkingPickup;
    }

    public String getmBkingPickupAt() {
        return mBkingPickupAt;
    }

    public void setmBkingPickupAt(String mBkingPickupAt) {
        this.mBkingPickupAt = mBkingPickupAt;
    }

    public String getmBkingReturn() {
        return mBkingReturn;
    }

    public void setmBkingReturn(String mBkingReturn) {
        this.mBkingReturn = mBkingReturn;
    }

    public String getmBkingReturnAt() {
        return mBkingReturnAt;
    }

    public void setmBkingReturnAt(String mBkingReturnAt) {
        this.mBkingReturnAt = mBkingReturnAt;
    }

    public String getmBkingFlight() {
        return mBkingFlight;
    }

    public void setmBkingFlight(String mBkingFlight) {
        this.mBkingFlight = mBkingFlight;
    }

    public String getmBkingCarType() {
        return mBkingCarType;
    }

    public void setmBkingCarType(String mBkingCarType) {
        this.mBkingCarType = mBkingCarType;
    }

    public String getmBkingCar() {
        return mBkingCar;
    }

    public void setmBkingCar(String mBkingCar) {
        this.mBkingCar = mBkingCar;
    }

    public String getmBkingBus() {
        return mBkingBus;
    }

    public void setmBkingBus(String mBkingBus) {
        this.mBkingBus = mBkingBus;
    }

    public String getmBkingDriver() {
        return mBkingDriver;
    }

    public void setmBkingDriver(String mBkingDriver) {
        this.mBkingDriver = mBkingDriver;
    }

    public String getmBkingFastag() {
        return mBkingFastag;
    }

    public void setmBkingFastag(String mBkingFastag) {
        this.mBkingFastag = mBkingFastag;
    }

    public String getmBkingHourRange() {
        return mBkingHourRange;
    }

    public void setmBkingHourRange(String mBkingHourRange) {
        this.mBkingHourRange = mBkingHourRange;
    }

    public String getmBkingKm() {
        return mBkingKm;
    }

    public void setmBkingKm(String mBkingKm) {
        this.mBkingKm = mBkingKm;
    }

    public String getmBkingPrice() {
        return mBkingPrice;
    }

    public void setmBkingPrice(String mBkingPrice) {
        this.mBkingPrice = mBkingPrice;
    }

    public String getmBkingCouponid() {
        return mBkingCouponid;
    }

    public void setmBkingCouponid(String mBkingCouponid) {
        this.mBkingCouponid = mBkingCouponid;
    }

    public String getmBkingDis() {
        return mBkingDis;
    }

    public void setmBkingDis(String mBkingDis) {
        this.mBkingDis = mBkingDis;
    }

    public String getmBkingDamt() {
        return mBkingDamt;
    }

    public void setmBkingDamt(String mBkingDamt) {
        this.mBkingDamt = mBkingDamt;
    }

    public String getmBkingTotal() {
        return mBkingTotal;
    }

    public void setmBkingTotal(String mBkingTotal) {
        this.mBkingTotal = mBkingTotal;
    }

    public String getmBkingPaidAmt() {
        return mBkingPaidAmt;
    }

    public void setmBkingPaidAmt(String mBkingPaidAmt) {
        this.mBkingPaidAmt = mBkingPaidAmt;
    }

    public String getmBkingRemainAmt() {
        return mBkingRemainAmt;
    }

    public void setmBkingRemainAmt(String mBkingRemainAmt) {
        this.mBkingRemainAmt = mBkingRemainAmt;
    }

    public String getmBkingPaymode() {
        return mBkingPaymode;
    }

    public void setmBkingPaymode(String mBkingPaymode) {
        this.mBkingPaymode = mBkingPaymode;
    }

    public String getmBkingPayStatus() {
        return mBkingPayStatus;
    }

    public void setmBkingPayStatus(String mBkingPayStatus) {
        this.mBkingPayStatus = mBkingPayStatus;
    }

    public String getmBkingStatus() {
        return mBkingStatus;
    }

    public void setmBkingStatus(String mBkingStatus) {
        this.mBkingStatus = mBkingStatus;
    }

    public String getmBkingTransId() {
        return mBkingTransId;
    }

    public void setmBkingTransId(String mBkingTransId) {
        this.mBkingTransId = mBkingTransId;
    }

    public String getmBkingAddedon() {
        return mBkingAddedon;
    }

    public void setmBkingAddedon(String mBkingAddedon) {
        this.mBkingAddedon = mBkingAddedon;
    }

    public String getmBkingUpdatedon() {
        return mBkingUpdatedon;
    }

    public void setmBkingUpdatedon(String mBkingUpdatedon) {
        this.mBkingUpdatedon = mBkingUpdatedon;
    }

    public String getmVendorId() {
        return mVendorId;
    }

    public void setmVendorId(String mVendorId) {
        this.mVendorId = mVendorId;
    }

    public String getmVendorName() {
        return mVendorName;
    }

    public void setmVendorName(String mVendorName) {
        this.mVendorName = mVendorName;
    }

    public String getmVendorMobile() {
        return mVendorMobile;
    }

    public void setmVendorMobile(String mVendorMobile) {
        this.mVendorMobile = mVendorMobile;
    }

    public String getmVendorEmail() {
        return mVendorEmail;
    }

    public void setmVendorEmail(String mVendorEmail) {
        this.mVendorEmail = mVendorEmail;
    }

    public String getmBranchId() {
        return mBranchId;
    }

    public void setmBranchId(String mBranchId) {
        this.mBranchId = mBranchId;
    }

    public String getmBranchTitle() {
        return mBranchTitle;
    }

    public void setmBranchTitle(String mBranchTitle) {
        this.mBranchTitle = mBranchTitle;
    }

    public String getmCtypeId() {
        return mCtypeId;
    }

    public void setmCtypeId(String mCtypeId) {
        this.mCtypeId = mCtypeId;
    }

    public String getmCtypeTitle() {
        return mCtypeTitle;
    }

    public void setmCtypeTitle(String mCtypeTitle) {
        this.mCtypeTitle = mCtypeTitle;
    }

    public String getmCtypeImg() {
        return mCtypeImg;
    }

    public void setmCtypeImg(String mCtypeImg) {
        this.mCtypeImg = mCtypeImg;
    }

    public String getmCtypeSeat() {
        return mCtypeSeat;
    }

    public void setmCtypeSeat(String mCtypeSeat) {
        this.mCtypeSeat = mCtypeSeat;
    }

    public String getmCtypeLuggage() {
        return mCtypeLuggage;
    }

    public void setmCtypeLuggage(String mCtypeLuggage) {
        this.mCtypeLuggage = mCtypeLuggage;
    }

    public String getmCtypeAC() {
        return mCtypeAC;
    }

    public void setmCtypeAC(String mCtypeAC) {
        this.mCtypeAC = mCtypeAC;
    }

    public String getmCtypeFuel() {
        return mCtypeFuel;
    }

    public void setmCtypeFuel(String mCtypeFuel) {
        this.mCtypeFuel = mCtypeFuel;
    }

    public String getmCtypeNumber() {
        return mCtypeNumber;
    }

    public void setmCtypeNumber(String mCtypeNumber) {
        this.mCtypeNumber = mCtypeNumber;
    }

    public String getmCtypeKm() {
        return mCtypeKm;
    }

    public void setmCtypeKm(String mCtypeKm) {
        this.mCtypeKm = mCtypeKm;
    }

    public String getmCtypePrice() {
        return mCtypePrice;
    }

    public void setmCtypePrice(String mCtypePrice) {
        this.mCtypePrice = mCtypePrice;
    }

    public String getmCtypeDrivetype() {
        return mCtypeDrivetype;
    }

    public void setmCtypeDrivetype(String mCtypeDrivetype) {
        this.mCtypeDrivetype = mCtypeDrivetype;
    }

    public String getmCtypeServtype() {
        return mCtypeServtype;
    }

    public void setmCtypeServtype(String mCtypeServtype) {
        this.mCtypeServtype = mCtypeServtype;
    }

    public String getmCtypeServtypeCat() {
        return mCtypeServtypeCat;
    }

    public void setmCtypeServtypeCat(String mCtypeServtypeCat) {
        this.mCtypeServtypeCat = mCtypeServtypeCat;
    }

    public String getmCtypeBranch() {
        return mCtypeBranch;
    }

    public void setmCtypeBranch(String mCtypeBranch) {
        this.mCtypeBranch = mCtypeBranch;
    }

    public String getmCtypeInclusion() {
        return mCtypeInclusion;
    }

    public void setmCtypeInclusion(String mCtypeInclusion) {
        this.mCtypeInclusion = mCtypeInclusion;
    }

    public String getmCtypeExclusion() {
        return mCtypeExclusion;
    }

    public void setmCtypeExclusion(String mCtypeExclusion) {
        this.mCtypeExclusion = mCtypeExclusion;
    }

    public String getmCtypeTc() {
        return mCtypeTc;
    }

    public void setmCtypeTc(String mCtypeTc) {
        this.mCtypeTc = mCtypeTc;
    }

    public String getmCtypeRegCerti() {
        return mCtypeRegCerti;
    }

    public void setmCtypeRegCerti(String mCtypeRegCerti) {
        this.mCtypeRegCerti = mCtypeRegCerti;
    }

    public String getmCtypeIssCerti() {
        return mCtypeIssCerti;
    }

    public void setmCtypeIssCerti(String mCtypeIssCerti) {
        this.mCtypeIssCerti = mCtypeIssCerti;
    }

    public String getmCtypePermit() {
        return mCtypePermit;
    }

    public void setmCtypePermit(String mCtypePermit) {
        this.mCtypePermit = mCtypePermit;
    }

    public String getmCtypeOwnercarImg() {
        return mCtypeOwnercarImg;
    }

    public void setmCtypeOwnercarImg(String mCtypeOwnercarImg) {
        this.mCtypeOwnercarImg = mCtypeOwnercarImg;
    }

    public String getmCtypeOrder() {
        return mCtypeOrder;
    }

    public void setmCtypeOrder(String mCtypeOrder) {
        this.mCtypeOrder = mCtypeOrder;
    }

    public String getmCtypeStatus() {
        return mCtypeStatus;
    }

    public void setmCtypeStatus(String mCtypeStatus) {
        this.mCtypeStatus = mCtypeStatus;
    }

    public String getmCtypeAddedby() {
        return mCtypeAddedby;
    }

    public void setmCtypeAddedby(String mCtypeAddedby) {
        this.mCtypeAddedby = mCtypeAddedby;
    }

    public String getmCtypeAddedon() {
        return mCtypeAddedon;
    }

    public void setmCtypeAddedon(String mCtypeAddedon) {
        this.mCtypeAddedon = mCtypeAddedon;
    }

    public String getmCtypeUpdatedon() {
        return mCtypeUpdatedon;
    }

    public void setmCtypeUpdatedon(String mCtypeUpdatedon) {
        this.mCtypeUpdatedon = mCtypeUpdatedon;
    }

    public String getmCustId() {
        return mCustId;
    }

    public void setmCustId(String mCustId) {
        this.mCustId = mCustId;
    }

    public String getmCustName() {
        return mCustName;
    }

    public void setmCustName(String mCustName) {
        this.mCustName = mCustName;
    }

    public String getmCustMobile() {
        return mCustMobile;
    }

    public void setmCustMobile(String mCustMobile) {
        this.mCustMobile = mCustMobile;
    }

    public String getmCustEmail() {
        return mCustEmail;
    }

    public void setmCustEmail(String mCustEmail) {
        this.mCustEmail = mCustEmail;
    }

    public String getmBusTitle() {
        return mBusTitle;
    }

    public void setmBusTitle(String mBusTitle) {
        this.mBusTitle = mBusTitle;
    }

    public String getmCarId() {
        return mCarId;
    }

    public void setmCarId(String mCarId) {
        this.mCarId = mCarId;
    }

    public String getmCarName() {
        return mCarName;
    }

    public void setmCarName(String mCarName) {
        this.mCarName = mCarName;
    }

    public String getmCarNumber() {
        return mCarNumber;
    }

    public void setmCarNumber(String mCarNumber) {
        this.mCarNumber = mCarNumber;
    }

    public String getmCarType() {
        return mCarType;
    }

    public void setmCarType(String mCarType) {
        this.mCarType = mCarType;
    }

    public String getmCarBrand() {
        return mCarBrand;
    }

    public void setmCarBrand(String mCarBrand) {
        this.mCarBrand = mCarBrand;
    }

    public String getmCarModel() {
        return mCarModel;
    }

    public void setmCarModel(String mCarModel) {
        this.mCarModel = mCarModel;
    }

    public String getmCarVendor() {
        return mCarVendor;
    }

    public void setmCarVendor(String mCarVendor) {
        this.mCarVendor = mCarVendor;
    }

    public String getmCarFuel() {
        return mCarFuel;
    }

    public void setmCarFuel(String mCarFuel) {
        this.mCarFuel = mCarFuel;
    }

    public String getmCarImg() {
        return mCarImg;
    }

    public void setmCarImg(String mCarImg) {
        this.mCarImg = mCarImg;
    }

    public String getmCarSeat() {
        return mCarSeat;
    }

    public void setmCarSeat(String mCarSeat) {
        this.mCarSeat = mCarSeat;
    }

    public String getmCarRegCerti() {
        return mCarRegCerti;
    }

    public void setmCarRegCerti(String mCarRegCerti) {
        this.mCarRegCerti = mCarRegCerti;
    }

    public String getmCarIssCerti() {
        return mCarIssCerti;
    }

    public void setmCarIssCerti(String mCarIssCerti) {
        this.mCarIssCerti = mCarIssCerti;
    }

    public String getmCarPermit() {
        return mCarPermit;
    }

    public void setmCarPermit(String mCarPermit) {
        this.mCarPermit = mCarPermit;
    }

    public String getmCarOwnercarImg() {
        return mCarOwnercarImg;
    }

    public void setmCarOwnercarImg(String mCarOwnercarImg) {
        this.mCarOwnercarImg = mCarOwnercarImg;
    }

    public String getmCarStatus() {
        return mCarStatus;
    }

    public void setmCarStatus(String mCarStatus) {
        this.mCarStatus = mCarStatus;
    }

    public String getmCarAddedon() {
        return mCarAddedon;
    }

    public void setmCarAddedon(String mCarAddedon) {
        this.mCarAddedon = mCarAddedon;
    }

    public String getmCarUpdatedon() {
        return mCarUpdatedon;
    }

    public void setmCarUpdatedon(String mCarUpdatedon) {
        this.mCarUpdatedon = mCarUpdatedon;
    }
}


//package com.logixhunt.carrorentaldriver.model;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class BookingListModel {
//
//    @SerializedName("m_bking_id")
//    @Expose
//    private String mBkingId;
//    @SerializedName("m_booking_id")
//    @Expose
//    private String mBookingId;
//    @SerializedName("m_bking_vendor")
//    @Expose
//    private String mBkingVendor;
//    @SerializedName("m_bking_user")
//    @Expose
//    private String mBkingUser;
//    @SerializedName("m_bking_username")
//    @Expose
//    private String mBkingUsername;
//    @SerializedName("m_bking_useremail")
//    @Expose
//    private String mBkingUseremail;
//    @SerializedName("m_bking_usermobile")
//    @Expose
//    private String mBkingUsermobile;
//    @SerializedName("m_bking_road_type")
//    @Expose
//    private String mBkingRoadType;
//    @SerializedName("m_bking_hour")
//    @Expose
//    private String mBkingHour;
//    @SerializedName("m_bking_service_type")
//    @Expose
//    private String mBkingServiceType;
//    @SerializedName("m_bking_from")
//    @Expose
//    private String mBkingFrom;
//    @SerializedName("m_bking_to")
//    @Expose
//    private String mBkingTo;
//    @SerializedName("m_bking_pickup_address")
//    @Expose
//    private String mBkingPickupAddress;
//    @SerializedName("m_bking_drop_address")
//    @Expose
//    private String mBkingDropAddress;
//    @SerializedName("m_bking_pickup")
//    @Expose
//    private String mBkingPickup;
//    @SerializedName("m_bking_pickup_at")
//    @Expose
//    private String mBkingPickupAt;
//    @SerializedName("m_bking_return")
//    @Expose
//    private String mBkingReturn;
//    @SerializedName("m_bking_return_at")
//    @Expose
//    private String mBkingReturnAt;
//    @SerializedName("m_bking_car_type")
//    @Expose
//    private String mBkingCarType;
//    @SerializedName("m_bking_car")
//    @Expose
//    private String mBkingCar;
//    @SerializedName("m_bking_driver")
//    @Expose
//    private String mBkingDriver;
//    @SerializedName("m_bking_km")
//    @Expose
//    private String mBkingKm;
//    @SerializedName("m_bking_price")
//    @Expose
//    private String mBkingPrice;
//    @SerializedName("m_bking_total")
//    @Expose
//    private String mBkingTotal;
//    @SerializedName("m_bking_paymode")
//    @Expose
//    private String mBkingPaymode;
//    @SerializedName("m_bking_pay_status")
//    @Expose
//    private String mBkingPayStatus;
//    @SerializedName("m_bking_status")
//    @Expose
//    private String mBkingStatus;
//    @SerializedName("m_bking_addedon")
//    @Expose
//    private String mBkingAddedon;
//    @SerializedName("m_bking_updatedon")
//    @Expose
//    private String mBkingUpdatedon;
//    @SerializedName("m_car_id")
//    @Expose
//    private String mCarId;
//    @SerializedName("m_car_name")
//    @Expose
//    private String mCarName;
//    @SerializedName("m_car_number")
//    @Expose
//    private String mCarNumber;
//    @SerializedName("m_car_type")
//    @Expose
//    private String mCarType;
//    @SerializedName("m_car_brand")
//    @Expose
//    private String mCarBrand;
//    @SerializedName("m_car_model")
//    @Expose
//    private String mCarModel;
//    @SerializedName("m_car_vendor")
//    @Expose
//    private String mCarVendor;
//    @SerializedName("m_car_fuel")
//    @Expose
//    private String mCarFuel;
//    @SerializedName("m_car_img")
//    @Expose
//    private String mCarImg;
//    @SerializedName("m_car_seat")
//    @Expose
//    private String mCarSeat;
//    @SerializedName("m_car_reg_certi")
//    @Expose
//    private String mCarRegCerti;
//    @SerializedName("m_car_iss_certi")
//    @Expose
//    private String mCarIssCerti;
//    @SerializedName("m_car_permit")
//    @Expose
//    private String mCarPermit;
//    @SerializedName("m_car_ownercar_img")
//    @Expose
//    private String mCarOwnercarImg;
//    @SerializedName("m_car_status")
//    @Expose
//    private String mCarStatus;
//    @SerializedName("m_car_addedon")
//    @Expose
//    private String mCarAddedon;
//    @SerializedName("m_car_updatedon")
//    @Expose
//    private String mCarUpdatedon;
//    @SerializedName("m_ctype_id")
//    @Expose
//    private String mCtypeId;
//    @SerializedName("m_ctype_title")
//    @Expose
//    private String mCtypeTitle;
//    @SerializedName("m_ctype_img")
//    @Expose
//    private String mCtypeImg;
//    @SerializedName("m_ctype_seat")
//    @Expose
//    private String mCtypeSeat;
//    @SerializedName("m_ctype_luggage")
//    @Expose
//    private String mCtypeLuggage;
//    @SerializedName("m_ctype_AC")
//    @Expose
//    private String mCtypeAC;
//    @SerializedName("m_ctype_price")
//    @Expose
//    private String mCtypePrice;
//    @SerializedName("m_ctype_drivetype")
//    @Expose
//    private String mCtypeDrivetype;
//    @SerializedName("m_ctype_servtype")
//    @Expose
//    private String mCtypeServtype;
//    @SerializedName("m_ctype_inclusion")
//    @Expose
//    private String mCtypeInclusion;
//    @SerializedName("m_ctype_exclusion")
//    @Expose
//    private String mCtypeExclusion;
//    @SerializedName("m_ctype_tc")
//    @Expose
//    private String mCtypeTc;
//    @SerializedName("m_ctype_status")
//    @Expose
//    private String mCtypeStatus;
//    @SerializedName("m_ctype_addedon")
//    @Expose
//    private String mCtypeAddedon;
//    @SerializedName("m_bking_type")
//    @Expose
//    private String mBkingType;
//    @SerializedName("m_bus_title")
//    @Expose
//    private String mBusTitle;
//    @SerializedName("m_ctype_number")
//    @Expose
//    private String mCtypeNumber;
//
//    public String getmBusTitle() {
//        return mBusTitle;
//    }
//
//    public void setmBusTitle(String mBusTitle) {
//        this.mBusTitle = mBusTitle;
//    }
//
//    public String getmBkingType() {
//        return mBkingType;
//    }
//
//    public void setmBkingType(String mBkingType) {
//        this.mBkingType = mBkingType;
//    }
//
//    public String getmBkingId() {
//        return mBkingId;
//    }
//
//    public void setmBkingId(String mBkingId) {
//        this.mBkingId = mBkingId;
//    }
//
//    public String getmBookingId() {
//        return mBookingId;
//    }
//
//    public void setmBookingId(String mBookingId) {
//        this.mBookingId = mBookingId;
//    }
//
//    public String getmBkingVendor() {
//        return mBkingVendor;
//    }
//
//    public void setmBkingVendor(String mBkingVendor) {
//        this.mBkingVendor = mBkingVendor;
//    }
//
//    public String getmBkingUser() {
//        return mBkingUser;
//    }
//
//    public void setmBkingUser(String mBkingUser) {
//        this.mBkingUser = mBkingUser;
//    }
//
//    public String getmBkingUsername() {
//        return mBkingUsername;
//    }
//
//    public void setmBkingUsername(String mBkingUsername) {
//        this.mBkingUsername = mBkingUsername;
//    }
//
//    public String getmBkingUseremail() {
//        return mBkingUseremail;
//    }
//
//    public void setmBkingUseremail(String mBkingUseremail) {
//        this.mBkingUseremail = mBkingUseremail;
//    }
//
//    public String getmBkingUsermobile() {
//        return mBkingUsermobile;
//    }
//
//    public void setmBkingUsermobile(String mBkingUsermobile) {
//        this.mBkingUsermobile = mBkingUsermobile;
//    }
//
//    public String getmBkingRoadType() {
//        return mBkingRoadType;
//    }
//
//    public void setmBkingRoadType(String mBkingRoadType) {
//        this.mBkingRoadType = mBkingRoadType;
//    }
//
//    public String getmBkingHour() {
//        return mBkingHour;
//    }
//
//    public void setmBkingHour(String mBkingHour) {
//        this.mBkingHour = mBkingHour;
//    }
//
//    public String getmBkingServiceType() {
//        return mBkingServiceType;
//    }
//
//    public void setmBkingServiceType(String mBkingServiceType) {
//        this.mBkingServiceType = mBkingServiceType;
//    }
//
//    public String getmBkingFrom() {
//        return mBkingFrom;
//    }
//
//    public void setmBkingFrom(String mBkingFrom) {
//        this.mBkingFrom = mBkingFrom;
//    }
//
//    public String getmBkingTo() {
//        return mBkingTo;
//    }
//
//    public void setmBkingTo(String mBkingTo) {
//        this.mBkingTo = mBkingTo;
//    }
//
//    public String getmBkingPickupAddress() {
//        return mBkingPickupAddress;
//    }
//
//    public void setmBkingPickupAddress(String mBkingPickupAddress) {
//        this.mBkingPickupAddress = mBkingPickupAddress;
//    }
//
//    public String getmBkingDropAddress() {
//        return mBkingDropAddress;
//    }
//
//    public void setmBkingDropAddress(String mBkingDropAddress) {
//        this.mBkingDropAddress = mBkingDropAddress;
//    }
//
//    public String getmBkingPickup() {
//        return mBkingPickup;
//    }
//
//    public void setmBkingPickup(String mBkingPickup) {
//        this.mBkingPickup = mBkingPickup;
//    }
//
//    public String getmBkingPickupAt() {
//        return mBkingPickupAt;
//    }
//
//    public void setmBkingPickupAt(String mBkingPickupAt) {
//        this.mBkingPickupAt = mBkingPickupAt;
//    }
//
//    public String getmBkingReturn() {
//        return mBkingReturn;
//    }
//
//    public void setmBkingReturn(String mBkingReturn) {
//        this.mBkingReturn = mBkingReturn;
//    }
//
//    public String getmBkingReturnAt() {
//        return mBkingReturnAt;
//    }
//
//    public void setmBkingReturnAt(String mBkingReturnAt) {
//        this.mBkingReturnAt = mBkingReturnAt;
//    }
//
//    public String getmBkingCarType() {
//        return mBkingCarType;
//    }
//
//    public void setmBkingCarType(String mBkingCarType) {
//        this.mBkingCarType = mBkingCarType;
//    }
//
//    public String getmBkingCar() {
//        return mBkingCar;
//    }
//
//    public void setmBkingCar(String mBkingCar) {
//        this.mBkingCar = mBkingCar;
//    }
//
//    public String getmBkingDriver() {
//        return mBkingDriver;
//    }
//
//    public void setmBkingDriver(String mBkingDriver) {
//        this.mBkingDriver = mBkingDriver;
//    }
//
//    public String getmBkingKm() {
//        return mBkingKm;
//    }
//
//    public void setmBkingKm(String mBkingKm) {
//        this.mBkingKm = mBkingKm;
//    }
//
//    public String getmBkingPrice() {
//        return mBkingPrice;
//    }
//
//    public void setmBkingPrice(String mBkingPrice) {
//        this.mBkingPrice = mBkingPrice;
//    }
//
//    public String getmBkingTotal() {
//        return mBkingTotal;
//    }
//
//    public void setmBkingTotal(String mBkingTotal) {
//        this.mBkingTotal = mBkingTotal;
//    }
//
//    public String getmBkingPaymode() {
//        return mBkingPaymode;
//    }
//
//    public void setmBkingPaymode(String mBkingPaymode) {
//        this.mBkingPaymode = mBkingPaymode;
//    }
//
//    public String getmBkingPayStatus() {
//        return mBkingPayStatus;
//    }
//
//    public void setmBkingPayStatus(String mBkingPayStatus) {
//        this.mBkingPayStatus = mBkingPayStatus;
//    }
//
//    public String getmBkingStatus() {
//        return mBkingStatus;
//    }
//
//    public void setmBkingStatus(String mBkingStatus) {
//        this.mBkingStatus = mBkingStatus;
//    }
//
//    public String getmBkingAddedon() {
//        return mBkingAddedon;
//    }
//
//    public void setmBkingAddedon(String mBkingAddedon) {
//        this.mBkingAddedon = mBkingAddedon;
//    }
//
//    public String getmBkingUpdatedon() {
//        return mBkingUpdatedon;
//    }
//
//    public void setmBkingUpdatedon(String mBkingUpdatedon) {
//        this.mBkingUpdatedon = mBkingUpdatedon;
//    }
//
//    public String getmCarId() {
//        return mCarId;
//    }
//
//    public void setmCarId(String mCarId) {
//        this.mCarId = mCarId;
//    }
//
//    public String getmCarName() {
//        return mCarName;
//    }
//
//    public void setmCarName(String mCarName) {
//        this.mCarName = mCarName;
//    }
//
//    public String getmCarNumber() {
//        return mCarNumber;
//    }
//
//    public void setmCarNumber(String mCarNumber) {
//        this.mCarNumber = mCarNumber;
//    }
//
//    public String getmCarType() {
//        return mCarType;
//    }
//
//    public void setmCarType(String mCarType) {
//        this.mCarType = mCarType;
//    }
//
//    public String getmCarBrand() {
//        return mCarBrand;
//    }
//
//    public void setmCarBrand(String mCarBrand) {
//        this.mCarBrand = mCarBrand;
//    }
//
//    public String getmCarModel() {
//        return mCarModel;
//    }
//
//    public void setmCarModel(String mCarModel) {
//        this.mCarModel = mCarModel;
//    }
//
//    public String getmCarVendor() {
//        return mCarVendor;
//    }
//
//    public void setmCarVendor(String mCarVendor) {
//        this.mCarVendor = mCarVendor;
//    }
//
//    public String getmCarFuel() {
//        return mCarFuel;
//    }
//
//    public void setmCarFuel(String mCarFuel) {
//        this.mCarFuel = mCarFuel;
//    }
//
//    public String getmCarImg() {
//        return mCarImg;
//    }
//
//    public void setmCarImg(String mCarImg) {
//        this.mCarImg = mCarImg;
//    }
//
//    public String getmCarSeat() {
//        return mCarSeat;
//    }
//
//    public void setmCarSeat(String mCarSeat) {
//        this.mCarSeat = mCarSeat;
//    }
//
//    public String getmCarRegCerti() {
//        return mCarRegCerti;
//    }
//
//    public void setmCarRegCerti(String mCarRegCerti) {
//        this.mCarRegCerti = mCarRegCerti;
//    }
//
//    public String getmCarIssCerti() {
//        return mCarIssCerti;
//    }
//
//    public void setmCarIssCerti(String mCarIssCerti) {
//        this.mCarIssCerti = mCarIssCerti;
//    }
//
//    public String getmCarPermit() {
//        return mCarPermit;
//    }
//
//    public void setmCarPermit(String mCarPermit) {
//        this.mCarPermit = mCarPermit;
//    }
//
//    public String getmCarOwnercarImg() {
//        return mCarOwnercarImg;
//    }
//
//    public void setmCarOwnercarImg(String mCarOwnercarImg) {
//        this.mCarOwnercarImg = mCarOwnercarImg;
//    }
//
//    public String getmCarStatus() {
//        return mCarStatus;
//    }
//
//    public void setmCarStatus(String mCarStatus) {
//        this.mCarStatus = mCarStatus;
//    }
//
//    public String getmCarAddedon() {
//        return mCarAddedon;
//    }
//
//    public void setmCarAddedon(String mCarAddedon) {
//        this.mCarAddedon = mCarAddedon;
//    }
//
//    public String getmCarUpdatedon() {
//        return mCarUpdatedon;
//    }
//
//    public void setmCarUpdatedon(String mCarUpdatedon) {
//        this.mCarUpdatedon = mCarUpdatedon;
//    }
//
//    public String getmCtypeId() {
//        return mCtypeId;
//    }
//
//    public void setmCtypeId(String mCtypeId) {
//        this.mCtypeId = mCtypeId;
//    }
//
//    public String getmCtypeTitle() {
//        return mCtypeTitle;
//    }
//
//    public void setmCtypeTitle(String mCtypeTitle) {
//        this.mCtypeTitle = mCtypeTitle;
//    }
//
//    public String getmCtypeImg() {
//        return mCtypeImg;
//    }
//
//    public void setmCtypeImg(String mCtypeImg) {
//        this.mCtypeImg = mCtypeImg;
//    }
//
//    public String getmCtypeSeat() {
//        return mCtypeSeat;
//    }
//
//    public void setmCtypeSeat(String mCtypeSeat) {
//        this.mCtypeSeat = mCtypeSeat;
//    }
//
//    public String getmCtypeLuggage() {
//        return mCtypeLuggage;
//    }
//
//    public void setmCtypeLuggage(String mCtypeLuggage) {
//        this.mCtypeLuggage = mCtypeLuggage;
//    }
//
//    public String getmCtypeAC() {
//        return mCtypeAC;
//    }
//
//    public void setmCtypeAC(String mCtypeAC) {
//        this.mCtypeAC = mCtypeAC;
//    }
//
//    public String getmCtypePrice() {
//        return mCtypePrice;
//    }
//
//    public void setmCtypePrice(String mCtypePrice) {
//        this.mCtypePrice = mCtypePrice;
//    }
//
//    public String getmCtypeDrivetype() {
//        return mCtypeDrivetype;
//    }
//
//    public void setmCtypeDrivetype(String mCtypeDrivetype) {
//        this.mCtypeDrivetype = mCtypeDrivetype;
//    }
//
//    public String getmCtypeServtype() {
//        return mCtypeServtype;
//    }
//
//    public void setmCtypeServtype(String mCtypeServtype) {
//        this.mCtypeServtype = mCtypeServtype;
//    }
//
//    public String getmCtypeInclusion() {
//        return mCtypeInclusion;
//    }
//
//    public void setmCtypeInclusion(String mCtypeInclusion) {
//        this.mCtypeInclusion = mCtypeInclusion;
//    }
//
//    public String getmCtypeExclusion() {
//        return mCtypeExclusion;
//    }
//
//    public void setmCtypeExclusion(String mCtypeExclusion) {
//        this.mCtypeExclusion = mCtypeExclusion;
//    }
//
//    public String getmCtypeTc() {
//        return mCtypeTc;
//    }
//
//    public void setmCtypeTc(String mCtypeTc) {
//        this.mCtypeTc = mCtypeTc;
//    }
//
//    public String getmCtypeStatus() {
//        return mCtypeStatus;
//    }
//
//    public void setmCtypeStatus(String mCtypeStatus) {
//        this.mCtypeStatus = mCtypeStatus;
//    }
//
//    public String getmCtypeAddedon() {
//        return mCtypeAddedon;
//    }
//
//    public void setmCtypeAddedon(String mCtypeAddedon) {
//        this.mCtypeAddedon = mCtypeAddedon;
//    }
//
//    public String getmCtypeNumber() {
//        return mCtypeNumber;
//    }
//
//    public void setmCtypeNumber(String mCtypeNumber) {
//        this.mCtypeNumber = mCtypeNumber;
//    }
//}
