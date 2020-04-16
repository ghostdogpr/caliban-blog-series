package client

import caliban.client.CalibanClientError.DecodingError
import caliban.client.FieldBuilder._
import caliban.client.SelectionBuilder._
import caliban.client._
import caliban.client.Operations._
import caliban.client.Value._

object TrainClient {

  sealed trait FacilityType extends scala.Product with scala.Serializable
  object FacilityType {
    case object ESCALATOR extends FacilityType
    case object ELEVATOR  extends FacilityType

    implicit val decoder: ScalarDecoder[FacilityType] = {
      case StringValue("ESCALATOR") => Right(FacilityType.ESCALATOR)
      case StringValue("ELEVATOR")  => Right(FacilityType.ELEVATOR)
      case other                    => Left(DecodingError(s"Can't build FacilityType from input $other"))
    }
    implicit val encoder: ArgEncoder[FacilityType] = new ArgEncoder[FacilityType] {
      override def encode(value: FacilityType): Value = value match {
        case FacilityType.ESCALATOR => EnumValue("ESCALATOR")
        case FacilityType.ELEVATOR  => EnumValue("ELEVATOR")
      }
      override def typeName: String = "FacilityType"
    }
  }

  sealed trait FacilityState extends scala.Product with scala.Serializable
  object FacilityState {
    case object ACTIVE   extends FacilityState
    case object INACTIVE extends FacilityState
    case object UNKNOWN  extends FacilityState

    implicit val decoder: ScalarDecoder[FacilityState] = {
      case StringValue("ACTIVE")   => Right(FacilityState.ACTIVE)
      case StringValue("INACTIVE") => Right(FacilityState.INACTIVE)
      case StringValue("UNKNOWN")  => Right(FacilityState.UNKNOWN)
      case other                   => Left(DecodingError(s"Can't build FacilityState from input $other"))
    }
    implicit val encoder: ArgEncoder[FacilityState] = new ArgEncoder[FacilityState] {
      override def encode(value: FacilityState): Value = value match {
        case FacilityState.ACTIVE   => EnumValue("ACTIVE")
        case FacilityState.INACTIVE => EnumValue("INACTIVE")
        case FacilityState.UNKNOWN  => EnumValue("UNKNOWN")
      }
      override def typeName: String = "FacilityState"
    }
  }

  type Route
  object Route {
    def parts[A](innerSelection: SelectionBuilder[RoutePart, A]): SelectionBuilder[Route, List[A]] =
      Field("parts", ListOf(Obj(innerSelection)))
    def from[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Route, Option[A]] =
      Field("from", OptionOf(Obj(innerSelection)))
    def to[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Route, Option[A]] =
      Field("to", OptionOf(Obj(innerSelection)))
  }

  type RoutePart
  object RoutePart {

    /**
     * Station where the part begins
     */
    def from[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RoutePart, A] =
      Field("from", Obj(innerSelection))
    def to[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RoutePart, A] =
      Field("to", Obj(innerSelection))
    def delay: SelectionBuilder[RoutePart, Option[Int]] = Field("delay", OptionOf(Scalar()))
    def product[A](innerSelection: SelectionBuilder[Product, A]): SelectionBuilder[RoutePart, Option[A]] =
      Field("product", OptionOf(Obj(innerSelection)))
    def direction: SelectionBuilder[RoutePart, String] = Field("direction", Scalar())
    def start: SelectionBuilder[RoutePart, String]     = Field("start", Scalar())
    def end: SelectionBuilder[RoutePart, String]       = Field("end", Scalar())
    def departingTrack[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[RoutePart, Option[A]] =
      Field("departingTrack", OptionOf(Obj(innerSelection)))
    def arrivingTrack[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[RoutePart, Option[A]] =
      Field("arrivingTrack", OptionOf(Obj(innerSelection)))
  }

  type Station
  object Station {
    def primaryEvaId: SelectionBuilder[Station, Option[Int]]     = Field("primaryEvaId", OptionOf(Scalar()))
    def stationNumber: SelectionBuilder[Station, Option[Int]]    = Field("stationNumber", OptionOf(Scalar()))
    def primaryRil100: SelectionBuilder[Station, Option[String]] = Field("primaryRil100", OptionOf(Scalar()))
    def name: SelectionBuilder[Station, String]                  = Field("name", Scalar())
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[Station, Option[A]] =
      Field("location", OptionOf(Obj(innerSelection)))
    def category: SelectionBuilder[Station, Int]                    = Field("category", Scalar())
    def priceCategory: SelectionBuilder[Station, Int]               = Field("priceCategory", Scalar())
    def hasParking: SelectionBuilder[Station, Boolean]              = Field("hasParking", Scalar())
    def hasBicycleParking: SelectionBuilder[Station, Boolean]       = Field("hasBicycleParking", Scalar())
    def hasLocalPublicTransport: SelectionBuilder[Station, Boolean] = Field("hasLocalPublicTransport", Scalar())
    def hasPublicFacilities: SelectionBuilder[Station, Boolean]     = Field("hasPublicFacilities", Scalar())
    def hasLockerSystem: SelectionBuilder[Station, Boolean]         = Field("hasLockerSystem", Scalar())
    def hasTaxiRank: SelectionBuilder[Station, Boolean]             = Field("hasTaxiRank", Scalar())
    def hasTravelNecessities: SelectionBuilder[Station, Boolean]    = Field("hasTravelNecessities", Scalar())
    def hasSteplessAccess: SelectionBuilder[Station, String]        = Field("hasSteplessAccess", Scalar())
    def hasMobilityService: SelectionBuilder[Station, String]       = Field("hasMobilityService", Scalar())
    def federalState: SelectionBuilder[Station, String]             = Field("federalState", Scalar())
    def regionalArea[A](innerSelection: SelectionBuilder[RegionalArea, A]): SelectionBuilder[Station, A] =
      Field("regionalArea", Obj(innerSelection))
    def facilities[A](innerSelection: SelectionBuilder[Facility, A]): SelectionBuilder[Station, List[A]] =
      Field("facilities", ListOf(Obj(innerSelection)))
    def mailingAddress[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[Station, A] =
      Field("mailingAddress", Obj(innerSelection))
    def DBInformationOpeningTimes[A](
      innerSelection: SelectionBuilder[OpeningTimes, A]
    ): SelectionBuilder[Station, Option[A]] = Field("DBInformationOpeningTimes", OptionOf(Obj(innerSelection)))
    def localServiceStaffAvailability[A](
      innerSelection: SelectionBuilder[OpeningTimes, A]
    ): SelectionBuilder[Station, Option[A]] = Field("localServiceStaffAvailability", OptionOf(Obj(innerSelection)))
    def aufgabentraeger[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      Field("aufgabentraeger", Obj(innerSelection))
    def timeTableOffice[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, Option[A]] =
      Field("timeTableOffice", OptionOf(Obj(innerSelection)))
    def szentrale[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      Field("szentrale", Obj(innerSelection))
    def stationManagement[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      Field("stationManagement", Obj(innerSelection))
    def timetable[A](innerSelection: SelectionBuilder[Timetable, A]): SelectionBuilder[Station, A] =
      Field("timetable", Obj(innerSelection))
    def parkingSpaces[A](innerSelection: SelectionBuilder[ParkingSpace, A]): SelectionBuilder[Station, List[A]] =
      Field("parkingSpaces", ListOf(Obj(innerSelection)))
    def hasSteamPermission: SelectionBuilder[Station, Boolean] = Field("hasSteamPermission", Scalar())
    def hasWiFi: SelectionBuilder[Station, Boolean]            = Field("hasWiFi", Scalar())
    def hasTravelCenter: SelectionBuilder[Station, Boolean]    = Field("hasTravelCenter", Scalar())
    def hasRailwayMission: SelectionBuilder[Station, Boolean]  = Field("hasRailwayMission", Scalar())
    def hasDBLounge: SelectionBuilder[Station, Boolean]        = Field("hasDBLounge", Scalar())
    def hasLostAndFound: SelectionBuilder[Station, Boolean]    = Field("hasLostAndFound", Scalar())
    def hasCarRental: SelectionBuilder[Station, Boolean]       = Field("hasCarRental", Scalar())
    def tracks[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[Station, List[A]] =
      Field("tracks", ListOf(Obj(innerSelection)))
    def picture[A](innerSelection: SelectionBuilder[Picture, A]): SelectionBuilder[Station, Option[A]] =
      Field("picture", OptionOf(Obj(innerSelection)))
  }

  type Location
  object Location {
    def latitude: SelectionBuilder[Location, Double]  = Field("latitude", Scalar())
    def longitude: SelectionBuilder[Location, Double] = Field("longitude", Scalar())
  }

  type RegionalArea
  object RegionalArea {
    def number: SelectionBuilder[RegionalArea, Int]       = Field("number", Scalar())
    def name: SelectionBuilder[RegionalArea, String]      = Field("name", Scalar())
    def shortName: SelectionBuilder[RegionalArea, String] = Field("shortName", Scalar())
  }

  type Facility
  object Facility {
    def description: SelectionBuilder[Facility, Option[String]]  = Field("description", OptionOf(Scalar()))
    def `type`: SelectionBuilder[Facility, FacilityType]         = Field("type", Scalar())
    def state: SelectionBuilder[Facility, FacilityState]         = Field("state", Scalar())
    def equipmentNumber: SelectionBuilder[Facility, Option[Int]] = Field("equipmentNumber", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[Facility, Option[A]] =
      Field("location", OptionOf(Obj(innerSelection)))
  }

  type MailingAddress
  object MailingAddress {
    def city: SelectionBuilder[MailingAddress, String]    = Field("city", Scalar())
    def zipcode: SelectionBuilder[MailingAddress, String] = Field("zipcode", Scalar())
    def street: SelectionBuilder[MailingAddress, String]  = Field("street", Scalar())
  }

  type OpeningTimes
  object OpeningTimes {
    def monday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("monday", OptionOf(Obj(innerSelection)))
    def tuesday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("tuesday", OptionOf(Obj(innerSelection)))
    def wednesday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("wednesday", OptionOf(Obj(innerSelection)))
    def thursday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("thursday", OptionOf(Obj(innerSelection)))
    def friday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("friday", OptionOf(Obj(innerSelection)))
    def saturday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("saturday", OptionOf(Obj(innerSelection)))
    def sunday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("sunday", OptionOf(Obj(innerSelection)))
    def holiday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      Field("holiday", OptionOf(Obj(innerSelection)))
  }

  type OpeningTime
  object OpeningTime {
    def from: SelectionBuilder[OpeningTime, String] = Field("from", Scalar())
    def to: SelectionBuilder[OpeningTime, String]   = Field("to", Scalar())
  }

  type StationContact
  object StationContact {
    def name: SelectionBuilder[StationContact, String]                = Field("name", Scalar())
    def shortName: SelectionBuilder[StationContact, Option[String]]   = Field("shortName", OptionOf(Scalar()))
    def email: SelectionBuilder[StationContact, Option[String]]       = Field("email", OptionOf(Scalar()))
    def number: SelectionBuilder[StationContact, Option[String]]      = Field("number", OptionOf(Scalar()))
    def phoneNumber: SelectionBuilder[StationContact, Option[String]] = Field("phoneNumber", OptionOf(Scalar()))
  }

  type Timetable
  object Timetable {
    def nextArrivals[A](innerSelection: SelectionBuilder[TrainInStation, A]): SelectionBuilder[Timetable, List[A]] =
      Field("nextArrivals", ListOf(Obj(innerSelection)))
    def nextDepatures[A](innerSelection: SelectionBuilder[TrainInStation, A]): SelectionBuilder[Timetable, List[A]] =
      Field("nextDepatures", ListOf(Obj(innerSelection)))
  }

  type TrainInStation
  object TrainInStation {
    def `type`: SelectionBuilder[TrainInStation, String]      = Field("type", Scalar())
    def trainNumber: SelectionBuilder[TrainInStation, String] = Field("trainNumber", Scalar())
    def platform: SelectionBuilder[TrainInStation, String]    = Field("platform", Scalar())
    def time: SelectionBuilder[TrainInStation, String]        = Field("time", Scalar())
    def stops: SelectionBuilder[TrainInStation, List[String]] = Field("stops", ListOf(Scalar()))
  }

  type ParkingSpace
  object ParkingSpace {
    def `type`: SelectionBuilder[ParkingSpace, Option[String]]         = Field("type", OptionOf(Scalar()))
    def id: SelectionBuilder[ParkingSpace, Int]                        = Field("id", Scalar())
    def name: SelectionBuilder[ParkingSpace, Option[String]]           = Field("name", OptionOf(Scalar()))
    def label: SelectionBuilder[ParkingSpace, Option[String]]          = Field("label", OptionOf(Scalar()))
    def spaceNumber: SelectionBuilder[ParkingSpace, Option[String]]    = Field("spaceNumber", OptionOf(Scalar()))
    def responsibility: SelectionBuilder[ParkingSpace, Option[String]] = Field("responsibility", OptionOf(Scalar()))
    def source: SelectionBuilder[ParkingSpace, Option[String]]         = Field("source", OptionOf(Scalar()))
    def nameDisplay: SelectionBuilder[ParkingSpace, Option[String]]    = Field("nameDisplay", OptionOf(Scalar()))
    def spaceType: SelectionBuilder[ParkingSpace, Option[String]]      = Field("spaceType", OptionOf(Scalar()))
    def spaceTypeEn: SelectionBuilder[ParkingSpace, Option[String]]    = Field("spaceTypeEn", OptionOf(Scalar()))
    def spaceTypeName: SelectionBuilder[ParkingSpace, Option[String]]  = Field("spaceTypeName", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      Field("location", OptionOf(Obj(innerSelection)))
    def url: SelectionBuilder[ParkingSpace, Option[String]]         = Field("url", OptionOf(Scalar()))
    def operator: SelectionBuilder[ParkingSpace, Option[String]]    = Field("operator", OptionOf(Scalar()))
    def operatorUrl: SelectionBuilder[ParkingSpace, Option[String]] = Field("operatorUrl", OptionOf(Scalar()))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      Field("address", OptionOf(Obj(innerSelection)))
    def distance: SelectionBuilder[ParkingSpace, Option[String]]       = Field("distance", OptionOf(Scalar()))
    def facilityType: SelectionBuilder[ParkingSpace, Option[String]]   = Field("facilityType", OptionOf(Scalar()))
    def facilityTypeEn: SelectionBuilder[ParkingSpace, Option[String]] = Field("facilityTypeEn", OptionOf(Scalar()))
    def openingHours: SelectionBuilder[ParkingSpace, Option[String]]   = Field("openingHours", OptionOf(Scalar()))
    def openingHoursEn: SelectionBuilder[ParkingSpace, Option[String]] = Field("openingHoursEn", OptionOf(Scalar()))
    def numberParkingPlaces: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("numberParkingPlaces", OptionOf(Scalar()))
    def numberHandicapedPlaces: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("numberHandicapedPlaces", OptionOf(Scalar()))
    def isSpecialProductDb: SelectionBuilder[ParkingSpace, Boolean] = Field("isSpecialProductDb", Scalar())
    def isOutOfService: SelectionBuilder[ParkingSpace, Boolean]     = Field("isOutOfService", Scalar())
    def station[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      Field("station", OptionOf(Obj(innerSelection)))
    def occupancy[A](innerSelection: SelectionBuilder[Occupancy, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      Field("occupancy", OptionOf(Obj(innerSelection)))
    def outOfServiceText: SelectionBuilder[ParkingSpace, Option[String]] = Field("outOfServiceText", OptionOf(Scalar()))
    def outOfServiceTextEn: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("outOfServiceTextEn", OptionOf(Scalar()))
    def reservation: SelectionBuilder[ParkingSpace, Option[String]]     = Field("reservation", OptionOf(Scalar()))
    def clearanceWidth: SelectionBuilder[ParkingSpace, Option[String]]  = Field("clearanceWidth", OptionOf(Scalar()))
    def clearanceHeight: SelectionBuilder[ParkingSpace, Option[String]] = Field("clearanceHeight", OptionOf(Scalar()))
    def allowedPropulsions: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("allowedPropulsions", OptionOf(Scalar()))
    def hasChargingStation: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("hasChargingStation", OptionOf(Scalar()))
    def tariffPrices[A](
      innerSelection: SelectionBuilder[ParkingPriceOption, A]
    ): SelectionBuilder[ParkingSpace, List[A]]                           = Field("tariffPrices", ListOf(Obj(innerSelection)))
    def outOfService: SelectionBuilder[ParkingSpace, Boolean]            = Field("outOfService", Scalar())
    def isDiscountDbBahnCard: SelectionBuilder[ParkingSpace, Boolean]    = Field("isDiscountDbBahnCard", Scalar())
    def isMonthVendingMachine: SelectionBuilder[ParkingSpace, Boolean]   = Field("isMonthVendingMachine", Scalar())
    def isDiscountDbBahnComfort: SelectionBuilder[ParkingSpace, Boolean] = Field("isDiscountDbBahnComfort", Scalar())
    def isDiscountDbParkAndRail: SelectionBuilder[ParkingSpace, Boolean] = Field("isDiscountDbParkAndRail", Scalar())
    def isMonthParkAndRide: SelectionBuilder[ParkingSpace, Boolean]      = Field("isMonthParkAndRide", Scalar())
    def isMonthSeason: SelectionBuilder[ParkingSpace, Boolean]           = Field("isMonthSeason", Scalar())
    def tariffDiscount: SelectionBuilder[ParkingSpace, Option[String]]   = Field("tariffDiscount", OptionOf(Scalar()))
    def tariffFreeParkingTime: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("tariffFreeParkingTime", OptionOf(Scalar()))
    def tariffDiscountEn: SelectionBuilder[ParkingSpace, Option[String]] = Field("tariffDiscountEn", OptionOf(Scalar()))
    def tariffPaymentOptions: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("tariffPaymentOptions", OptionOf(Scalar()))
    def tariffPaymentCustomerCards: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("tariffPaymentCustomerCards", OptionOf(Scalar()))
    def tariffFreeParkingTimeEn: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("tariffFreeParkingTimeEn", OptionOf(Scalar()))
    def tariffPaymentOptionsEn: SelectionBuilder[ParkingSpace, Option[String]] =
      Field("tariffPaymentOptionsEn", OptionOf(Scalar()))
    def slogan: SelectionBuilder[ParkingSpace, Option[String]]   = Field("slogan", OptionOf(Scalar()))
    def sloganEn: SelectionBuilder[ParkingSpace, Option[String]] = Field("sloganEn", OptionOf(Scalar()))
  }

  type Occupancy
  object Occupancy {
    def validData: SelectionBuilder[Occupancy, Boolean]  = Field("validData", Scalar())
    def timestamp: SelectionBuilder[Occupancy, String]   = Field("timestamp", Scalar())
    def timeSegment: SelectionBuilder[Occupancy, String] = Field("timeSegment", Scalar())
    def category: SelectionBuilder[Occupancy, Int]       = Field("category", Scalar())
    def text: SelectionBuilder[Occupancy, String]        = Field("text", Scalar())
  }

  type ParkingPriceOption
  object ParkingPriceOption {
    def id: SelectionBuilder[ParkingPriceOption, Int]               = Field("id", Scalar())
    def duration: SelectionBuilder[ParkingPriceOption, String]      = Field("duration", Scalar())
    def price: SelectionBuilder[ParkingPriceOption, Option[Double]] = Field("price", OptionOf(Scalar()))
  }

  type Track
  object Track {
    def platform: SelectionBuilder[Track, String] = Field("platform", Scalar())
    def number: SelectionBuilder[Track, String]   = Field("number", Scalar())
    def name: SelectionBuilder[Track, String]     = Field("name", Scalar())

    /**
     * Length of the platform in cm
     */
    def length: SelectionBuilder[Track, Option[Int]] = Field("length", OptionOf(Scalar()))

    /**
     * Height of the platform in cm
     */
    def height: SelectionBuilder[Track, Int] = Field("height", Scalar())
  }

  type Picture
  object Picture {
    def id: SelectionBuilder[Picture, Int]         = Field("id", Scalar())
    def url: SelectionBuilder[Picture, String]     = Field("url", Scalar())
    def license: SelectionBuilder[Picture, String] = Field("license", Scalar())
    def photographer[A](innerSelection: SelectionBuilder[Photographer, A]): SelectionBuilder[Picture, A] =
      Field("photographer", Obj(innerSelection))
  }

  type Photographer
  object Photographer {
    def name: SelectionBuilder[Photographer, String] = Field("name", Scalar())
    def url: SelectionBuilder[Photographer, String]  = Field("url", Scalar())
  }

  type Product
  object Product {
    def name: SelectionBuilder[Product, Option[String]]        = Field("name", OptionOf(Scalar()))
    def `class`: SelectionBuilder[Product, Option[Int]]        = Field("class", OptionOf(Scalar()))
    def productCode: SelectionBuilder[Product, Option[Int]]    = Field("productCode", OptionOf(Scalar()))
    def productName: SelectionBuilder[Product, Option[String]] = Field("productName", OptionOf(Scalar()))
  }

  type Searchable
  object Searchable {
    def stations[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Searchable, List[A]] =
      Field("stations", ListOf(Obj(innerSelection)))
    def operationLocations[A](
      innerSelection: SelectionBuilder[OperationLocation, A]
    ): SelectionBuilder[Searchable, List[A]] = Field("operationLocations", ListOf(Obj(innerSelection)))
  }

  type OperationLocation
  object OperationLocation {
    def id: SelectionBuilder[OperationLocation, Option[String]]           = Field("id", OptionOf(Scalar()))
    def abbrev: SelectionBuilder[OperationLocation, String]               = Field("abbrev", Scalar())
    def name: SelectionBuilder[OperationLocation, String]                 = Field("name", Scalar())
    def shortName: SelectionBuilder[OperationLocation, String]            = Field("shortName", Scalar())
    def `type`: SelectionBuilder[OperationLocation, String]               = Field("type", Scalar())
    def status: SelectionBuilder[OperationLocation, Option[String]]       = Field("status", OptionOf(Scalar()))
    def locationCode: SelectionBuilder[OperationLocation, Option[String]] = Field("locationCode", OptionOf(Scalar()))
    def UIC: SelectionBuilder[OperationLocation, String]                  = Field("UIC", Scalar())
    def regionId: SelectionBuilder[OperationLocation, Option[String]]     = Field("regionId", OptionOf(Scalar()))
    def validFrom: SelectionBuilder[OperationLocation, String]            = Field("validFrom", Scalar())
    def validTill: SelectionBuilder[OperationLocation, Option[String]]    = Field("validTill", OptionOf(Scalar()))
    def timeTableRelevant: SelectionBuilder[OperationLocation, Option[Boolean]] =
      Field("timeTableRelevant", OptionOf(Scalar()))
    def borderStation: SelectionBuilder[OperationLocation, Option[Boolean]] = Field("borderStation", OptionOf(Scalar()))
  }

  type Nearby
  object Nearby {
    def stations[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[Station, A]
    ): SelectionBuilder[Nearby, List[A]] =
      Field(
        "stations",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count), Argument("offset", offset))
      )
    def parkingSpaces[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[ParkingSpace, A]
    ): SelectionBuilder[Nearby, List[A]] =
      Field(
        "parkingSpaces",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count), Argument("offset", offset))
      )
    def travelCenters[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[TravelCenter, A]
    ): SelectionBuilder[Nearby, List[A]] =
      Field(
        "travelCenters",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count), Argument("offset", offset))
      )
    def flinksterCars[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[FlinksterCar, A]
    ): SelectionBuilder[Nearby, List[A]] =
      Field(
        "flinksterCars",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count), Argument("offset", offset))
      )
    def bikes[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[FlinksterBike, A]
    ): SelectionBuilder[Nearby, List[A]] =
      Field(
        "bikes",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count), Argument("offset", offset))
      )
  }

  type TravelCenter
  object TravelCenter {
    def id: SelectionBuilder[TravelCenter, Option[Int]]      = Field("id", OptionOf(Scalar()))
    def name: SelectionBuilder[TravelCenter, Option[String]] = Field("name", OptionOf(Scalar()))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[TravelCenter, Option[A]] =
      Field("address", OptionOf(Obj(innerSelection)))
    def `type`: SelectionBuilder[TravelCenter, Option[String]] = Field("type", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[TravelCenter, Option[A]] =
      Field("location", OptionOf(Obj(innerSelection)))
  }

  type FlinksterCar
  object FlinksterCar {
    def id: SelectionBuilder[FlinksterCar, String]          = Field("id", Scalar())
    def name: SelectionBuilder[FlinksterCar, String]        = Field("name", Scalar())
    def description: SelectionBuilder[FlinksterCar, String] = Field("description", Scalar())
    def attributes[A](innerSelection: SelectionBuilder[CarAttributes, A]): SelectionBuilder[FlinksterCar, A] =
      Field("attributes", Obj(innerSelection))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterCar, A] =
      Field("location", Obj(innerSelection))
    def priceOptions[A](
      innerSelection: SelectionBuilder[PriceOption, A]
    ): SelectionBuilder[FlinksterCar, List[Option[A]]] = Field("priceOptions", ListOf(OptionOf(Obj(innerSelection))))
    def equipment[A](innerSelection: SelectionBuilder[CarEquipment, A]): SelectionBuilder[FlinksterCar, A] =
      Field("equipment", Obj(innerSelection))
    def rentalModel: SelectionBuilder[FlinksterCar, String] = Field("rentalModel", Scalar())
    def parkingArea[A](innerSelection: SelectionBuilder[FlinksterParkingArea, A]): SelectionBuilder[FlinksterCar, A] =
      Field("parkingArea", Obj(innerSelection))
    def category: SelectionBuilder[FlinksterCar, String] = Field("category", Scalar())
    def url: SelectionBuilder[FlinksterCar, String]      = Field("url", Scalar())
  }

  type CarAttributes
  object CarAttributes {
    def seats: SelectionBuilder[CarAttributes, Int]                   = Field("seats", Scalar())
    def color: SelectionBuilder[CarAttributes, String]                = Field("color", Scalar())
    def doors: SelectionBuilder[CarAttributes, Int]                   = Field("doors", Scalar())
    def transmissionType: SelectionBuilder[CarAttributes, String]     = Field("transmissionType", Scalar())
    def licensePlate: SelectionBuilder[CarAttributes, Option[String]] = Field("licensePlate", OptionOf(Scalar()))
    def fillLevel: SelectionBuilder[CarAttributes, Option[Int]]       = Field("fillLevel", OptionOf(Scalar()))
    def fuel: SelectionBuilder[CarAttributes, Option[String]]         = Field("fuel", OptionOf(Scalar()))
  }

  type PriceOption
  object PriceOption {
    def interval: SelectionBuilder[PriceOption, Option[Int]]   = Field("interval", OptionOf(Scalar()))
    def `type`: SelectionBuilder[PriceOption, String]          = Field("type", Scalar())
    def grossamount: SelectionBuilder[PriceOption, Double]     = Field("grossamount", Scalar())
    def currency: SelectionBuilder[PriceOption, String]        = Field("currency", Scalar())
    def taxrate: SelectionBuilder[PriceOption, Double]         = Field("taxrate", Scalar())
    def preferredprice: SelectionBuilder[PriceOption, Boolean] = Field("preferredprice", Scalar())
  }

  type CarEquipment
  object CarEquipment {
    def cdPlayer: SelectionBuilder[CarEquipment, Option[Boolean]]        = Field("cdPlayer", OptionOf(Scalar()))
    def airConditioning: SelectionBuilder[CarEquipment, Option[Boolean]] = Field("airConditioning", OptionOf(Scalar()))
    def navigationSystem: SelectionBuilder[CarEquipment, Option[Boolean]] =
      Field("navigationSystem", OptionOf(Scalar()))
    def roofRailing: SelectionBuilder[CarEquipment, Option[Boolean]] = Field("roofRailing", OptionOf(Scalar()))
    def particulateFilter: SelectionBuilder[CarEquipment, Option[Boolean]] =
      Field("particulateFilter", OptionOf(Scalar()))
    def audioInline: SelectionBuilder[CarEquipment, Option[Boolean]] = Field("audioInline", OptionOf(Scalar()))
    def tyreType: SelectionBuilder[CarEquipment, Option[String]]     = Field("tyreType", OptionOf(Scalar()))
    def bluetoothHandsFreeCalling: SelectionBuilder[CarEquipment, Option[Boolean]] =
      Field("bluetoothHandsFreeCalling", OptionOf(Scalar()))
    def cruiseControl: SelectionBuilder[CarEquipment, Option[Boolean]] = Field("cruiseControl", OptionOf(Scalar()))
    def passengerAirbagTurnOff: SelectionBuilder[CarEquipment, Option[Boolean]] =
      Field("passengerAirbagTurnOff", OptionOf(Scalar()))
    def isofixSeatFittings: SelectionBuilder[CarEquipment, Option[Boolean]] =
      Field("isofixSeatFittings", OptionOf(Scalar()))
  }

  type FlinksterParkingArea
  object FlinksterParkingArea {
    def id: SelectionBuilder[FlinksterParkingArea, String]   = Field("id", Scalar())
    def url: SelectionBuilder[FlinksterParkingArea, String]  = Field("url", Scalar())
    def name: SelectionBuilder[FlinksterParkingArea, String] = Field("name", Scalar())
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[FlinksterParkingArea, A] =
      Field("address", Obj(innerSelection))
    def parkingDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      Field("parkingDescription", OptionOf(Scalar()))
    def accessDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      Field("accessDescription", OptionOf(Scalar()))
    def locationDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      Field("locationDescription", OptionOf(Scalar()))
    def publicTransport: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      Field("publicTransport", OptionOf(Scalar()))
    def provider[A](innerSelection: SelectionBuilder[FlinksterProvider, A]): SelectionBuilder[FlinksterParkingArea, A] =
      Field("provider", Obj(innerSelection))
    def `type`: SelectionBuilder[FlinksterParkingArea, String] = Field("type", Scalar())
    def position[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterParkingArea, A] =
      Field("position", Obj(innerSelection))
    def GeoJSON[A](innerSelection: SelectionBuilder[GeoJSON, A]): SelectionBuilder[FlinksterParkingArea, Option[A]] =
      Field("GeoJSON", OptionOf(Obj(innerSelection)))
  }

  type FlinksterProvider
  object FlinksterProvider {
    def url: SelectionBuilder[FlinksterProvider, String]           = Field("url", Scalar())
    def areaId: SelectionBuilder[FlinksterProvider, Int]           = Field("areaId", Scalar())
    def networkIds: SelectionBuilder[FlinksterProvider, List[Int]] = Field("networkIds", ListOf(Scalar()))
  }

  type GeoJSON
  object GeoJSON {
    def `type`: SelectionBuilder[GeoJSON, String] = Field("type", Scalar())
    def features[A](innerSelection: SelectionBuilder[GeoFeature, A]): SelectionBuilder[GeoJSON, List[A]] =
      Field("features", ListOf(Obj(innerSelection)))
  }

  type GeoFeature
  object GeoFeature {
    def `type`: SelectionBuilder[GeoFeature, String] = Field("type", Scalar())
    def properties[A](innerSelection: SelectionBuilder[GeoProperties, A]): SelectionBuilder[GeoFeature, A] =
      Field("properties", Obj(innerSelection))
    def geometry[A](innerSelection: SelectionBuilder[GeoPolygon, A]): SelectionBuilder[GeoFeature, A] =
      Field("geometry", Obj(innerSelection))
  }

  type GeoProperties
  object GeoProperties {
    def name: SelectionBuilder[GeoProperties, String] = Field("name", Scalar())
  }

  type GeoPolygon
  object GeoPolygon {
    def `type`: SelectionBuilder[GeoPolygon, String] = Field("type", Scalar())
    def coordinates: SelectionBuilder[GeoPolygon, List[Option[List[Option[List[Option[List[Option[Double]]]]]]]]] =
      Field("coordinates", ListOf(OptionOf(ListOf(OptionOf(ListOf(OptionOf(ListOf(OptionOf(Scalar())))))))))
  }

  type FlinksterBike
  object FlinksterBike {
    def id: SelectionBuilder[FlinksterBike, String]          = Field("id", Scalar())
    def url: SelectionBuilder[FlinksterBike, String]         = Field("url", Scalar())
    def name: SelectionBuilder[FlinksterBike, String]        = Field("name", Scalar())
    def description: SelectionBuilder[FlinksterBike, String] = Field("description", Scalar())
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterBike, A] =
      Field("location", Obj(innerSelection))
    def priceOptions[A](
      innerSelection: SelectionBuilder[PriceOption, A]
    ): SelectionBuilder[FlinksterBike, List[Option[A]]] = Field("priceOptions", ListOf(OptionOf(Obj(innerSelection))))
    def attributes[A](innerSelection: SelectionBuilder[BikeAttributes, A]): SelectionBuilder[FlinksterBike, A] =
      Field("attributes", Obj(innerSelection))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[FlinksterBike, A] =
      Field("address", Obj(innerSelection))
    def rentalModel: SelectionBuilder[FlinksterBike, String]         = Field("rentalModel", Scalar())
    def `type`: SelectionBuilder[FlinksterBike, String]              = Field("type", Scalar())
    def providerRentalObjectId: SelectionBuilder[FlinksterBike, Int] = Field("providerRentalObjectId", Scalar())
    def parkingArea[A](innerSelection: SelectionBuilder[FlinksterParkingArea, A]): SelectionBuilder[FlinksterBike, A] =
      Field("parkingArea", Obj(innerSelection))
    def bookingUrl: SelectionBuilder[FlinksterBike, String] = Field("bookingUrl", Scalar())
  }

  type BikeAttributes
  object BikeAttributes {
    def licensePlate: SelectionBuilder[BikeAttributes, String] = Field("licensePlate", Scalar())
  }

  type Query = RootQuery
  object Query {
    def routing[A](from: Int, to: Int)(
      innerSelection: SelectionBuilder[Route, A]
    ): SelectionBuilder[RootQuery, List[A]] =
      Field("routing", ListOf(Obj(innerSelection)), arguments = List(Argument("from", from), Argument("to", to)))
    def stationWithEvaId[A](
      evaId: Int
    )(innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("stationWithEvaId", OptionOf(Obj(innerSelection)), arguments = List(Argument("evaId", evaId)))
    def stationWithStationNumber[A](
      stationNumber: Int
    )(innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field(
        "stationWithStationNumber",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("stationNumber", stationNumber))
      )
    def stationWithRill100[A](
      rill100: String
    )(innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("stationWithRill100", OptionOf(Obj(innerSelection)), arguments = List(Argument("rill100", rill100)))
    def search[A](
      searchTerm: Option[String] = None
    )(innerSelection: SelectionBuilder[Searchable, A]): SelectionBuilder[RootQuery, A] =
      Field("search", Obj(innerSelection), arguments = List(Argument("searchTerm", searchTerm)))
    def nearby[A](latitude: Double, longitude: Double, radius: Option[Int] = None)(
      innerSelection: SelectionBuilder[Nearby, A]
    ): SelectionBuilder[RootQuery, A] =
      Field(
        "nearby",
        Obj(innerSelection),
        arguments = List(Argument("latitude", latitude), Argument("longitude", longitude), Argument("radius", radius))
      )
    def parkingSpace[A](
      id: Option[Int] = None
    )(innerSelection: SelectionBuilder[ParkingSpace, A]): SelectionBuilder[RootQuery, Option[A]] =
      Field("parkingSpace", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id)))
  }

}

