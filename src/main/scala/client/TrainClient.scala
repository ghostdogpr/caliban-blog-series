package client

import caliban.client.CalibanClientError.DecodingError
import caliban.client.FieldBuilder._
import caliban.client._
import caliban.client.__Value._

object TrainClient {

  sealed trait FacilityState extends scala.Product with scala.Serializable { def value: String }
  object FacilityState {
    case object ACTIVE   extends FacilityState { val value: String = "ACTIVE"   }
    case object INACTIVE extends FacilityState { val value: String = "INACTIVE" }
    case object UNKNOWN  extends FacilityState { val value: String = "UNKNOWN"  }

    implicit val decoder: ScalarDecoder[FacilityState] = {
      case __StringValue("ACTIVE")   => Right(FacilityState.ACTIVE)
      case __StringValue("INACTIVE") => Right(FacilityState.INACTIVE)
      case __StringValue("UNKNOWN")  => Right(FacilityState.UNKNOWN)
      case other                     => Left(DecodingError(s"Can't build FacilityState from input $other"))
    }
    implicit val encoder: ArgEncoder[FacilityState] = {
      case FacilityState.ACTIVE   => __EnumValue("ACTIVE")
      case FacilityState.INACTIVE => __EnumValue("INACTIVE")
      case FacilityState.UNKNOWN  => __EnumValue("UNKNOWN")
    }

    val values: Vector[FacilityState] = Vector(ACTIVE, INACTIVE, UNKNOWN)
  }

  sealed trait FacilityType extends scala.Product with scala.Serializable { def value: String }
  object FacilityType {
    case object ESCALATOR extends FacilityType { val value: String = "ESCALATOR" }
    case object ELEVATOR  extends FacilityType { val value: String = "ELEVATOR"  }

    implicit val decoder: ScalarDecoder[FacilityType] = {
      case __StringValue("ESCALATOR") => Right(FacilityType.ESCALATOR)
      case __StringValue("ELEVATOR")  => Right(FacilityType.ELEVATOR)
      case other                      => Left(DecodingError(s"Can't build FacilityType from input $other"))
    }
    implicit val encoder: ArgEncoder[FacilityType] = {
      case FacilityType.ESCALATOR => __EnumValue("ESCALATOR")
      case FacilityType.ELEVATOR  => __EnumValue("ELEVATOR")
    }

    val values: Vector[FacilityType] = Vector(ESCALATOR, ELEVATOR)
  }

  type BikeAttributes
  object BikeAttributes {
    def licensePlate: SelectionBuilder[BikeAttributes, String] =
      _root_.caliban.client.SelectionBuilder.Field("licensePlate", Scalar())
  }

  type CarAttributes
  object CarAttributes {
    def seats: SelectionBuilder[CarAttributes, Int]    = _root_.caliban.client.SelectionBuilder.Field("seats", Scalar())
    def color: SelectionBuilder[CarAttributes, String] = _root_.caliban.client.SelectionBuilder.Field("color", Scalar())
    def doors: SelectionBuilder[CarAttributes, Int]    = _root_.caliban.client.SelectionBuilder.Field("doors", Scalar())
    def transmissionType: SelectionBuilder[CarAttributes, String] =
      _root_.caliban.client.SelectionBuilder.Field("transmissionType", Scalar())
    def licensePlate: SelectionBuilder[CarAttributes, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("licensePlate", OptionOf(Scalar()))
    def fillLevel: SelectionBuilder[CarAttributes, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("fillLevel", OptionOf(Scalar()))
    def fuel: SelectionBuilder[CarAttributes, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("fuel", OptionOf(Scalar()))
  }

  type CarEquipment
  object CarEquipment {
    def cdPlayer: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("cdPlayer", OptionOf(Scalar()))
    def airConditioning: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("airConditioning", OptionOf(Scalar()))
    def navigationSystem: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("navigationSystem", OptionOf(Scalar()))
    def roofRailing: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("roofRailing", OptionOf(Scalar()))
    def particulateFilter: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("particulateFilter", OptionOf(Scalar()))
    def audioInline: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("audioInline", OptionOf(Scalar()))
    def tyreType: SelectionBuilder[CarEquipment, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tyreType", OptionOf(Scalar()))
    def bluetoothHandsFreeCalling: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("bluetoothHandsFreeCalling", OptionOf(Scalar()))
    def cruiseControl: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("cruiseControl", OptionOf(Scalar()))
    def passengerAirbagTurnOff: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("passengerAirbagTurnOff", OptionOf(Scalar()))
    def isofixSeatFittings: SelectionBuilder[CarEquipment, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("isofixSeatFittings", OptionOf(Scalar()))
  }

  type Facility
  object Facility {
    def description: SelectionBuilder[Facility, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("description", OptionOf(Scalar()))
    def `type`: SelectionBuilder[Facility, FacilityType] =
      _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def state: SelectionBuilder[Facility, FacilityState] =
      _root_.caliban.client.SelectionBuilder.Field("state", Scalar())
    def equipmentNumber: SelectionBuilder[Facility, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("equipmentNumber", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[Facility, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("location", OptionOf(Obj(innerSelection)))
  }

  type FlinksterBike
  object FlinksterBike {
    def id: SelectionBuilder[FlinksterBike, String]   = _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def url: SelectionBuilder[FlinksterBike, String]  = _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
    def name: SelectionBuilder[FlinksterBike, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def description: SelectionBuilder[FlinksterBike, String] =
      _root_.caliban.client.SelectionBuilder.Field("description", Scalar())
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterBike, A] =
      _root_.caliban.client.SelectionBuilder.Field("location", Obj(innerSelection))
    def priceOptions[A](
      innerSelection: SelectionBuilder[PriceOption, A]
    ): SelectionBuilder[FlinksterBike, List[Option[A]]] =
      _root_.caliban.client.SelectionBuilder.Field("priceOptions", ListOf(OptionOf(Obj(innerSelection))))
    def attributes[A](innerSelection: SelectionBuilder[BikeAttributes, A]): SelectionBuilder[FlinksterBike, A] =
      _root_.caliban.client.SelectionBuilder.Field("attributes", Obj(innerSelection))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[FlinksterBike, A] =
      _root_.caliban.client.SelectionBuilder.Field("address", Obj(innerSelection))
    def rentalModel: SelectionBuilder[FlinksterBike, String] =
      _root_.caliban.client.SelectionBuilder.Field("rentalModel", Scalar())
    def `type`: SelectionBuilder[FlinksterBike, String] = _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def providerRentalObjectId: SelectionBuilder[FlinksterBike, Int] =
      _root_.caliban.client.SelectionBuilder.Field("providerRentalObjectId", Scalar())
    def parkingArea[A](innerSelection: SelectionBuilder[FlinksterParkingArea, A]): SelectionBuilder[FlinksterBike, A] =
      _root_.caliban.client.SelectionBuilder.Field("parkingArea", Obj(innerSelection))
    def bookingUrl: SelectionBuilder[FlinksterBike, String] =
      _root_.caliban.client.SelectionBuilder.Field("bookingUrl", Scalar())
  }

  type FlinksterCar
  object FlinksterCar {
    def id: SelectionBuilder[FlinksterCar, String]   = _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def name: SelectionBuilder[FlinksterCar, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def description: SelectionBuilder[FlinksterCar, String] =
      _root_.caliban.client.SelectionBuilder.Field("description", Scalar())
    def attributes[A](innerSelection: SelectionBuilder[CarAttributes, A]): SelectionBuilder[FlinksterCar, A] =
      _root_.caliban.client.SelectionBuilder.Field("attributes", Obj(innerSelection))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterCar, A] =
      _root_.caliban.client.SelectionBuilder.Field("location", Obj(innerSelection))
    def priceOptions[A](
      innerSelection: SelectionBuilder[PriceOption, A]
    ): SelectionBuilder[FlinksterCar, List[Option[A]]] =
      _root_.caliban.client.SelectionBuilder.Field("priceOptions", ListOf(OptionOf(Obj(innerSelection))))
    def equipment[A](innerSelection: SelectionBuilder[CarEquipment, A]): SelectionBuilder[FlinksterCar, A] =
      _root_.caliban.client.SelectionBuilder.Field("equipment", Obj(innerSelection))
    def rentalModel: SelectionBuilder[FlinksterCar, String] =
      _root_.caliban.client.SelectionBuilder.Field("rentalModel", Scalar())
    def parkingArea[A](innerSelection: SelectionBuilder[FlinksterParkingArea, A]): SelectionBuilder[FlinksterCar, A] =
      _root_.caliban.client.SelectionBuilder.Field("parkingArea", Obj(innerSelection))
    def category: SelectionBuilder[FlinksterCar, String] =
      _root_.caliban.client.SelectionBuilder.Field("category", Scalar())
    def url: SelectionBuilder[FlinksterCar, String] = _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
  }

  type FlinksterParkingArea
  object FlinksterParkingArea {
    def id: SelectionBuilder[FlinksterParkingArea, String] =
      _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def url: SelectionBuilder[FlinksterParkingArea, String] =
      _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
    def name: SelectionBuilder[FlinksterParkingArea, String] =
      _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[FlinksterParkingArea, A] =
      _root_.caliban.client.SelectionBuilder.Field("address", Obj(innerSelection))
    def parkingDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("parkingDescription", OptionOf(Scalar()))
    def accessDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("accessDescription", OptionOf(Scalar()))
    def locationDescription: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("locationDescription", OptionOf(Scalar()))
    def publicTransport: SelectionBuilder[FlinksterParkingArea, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("publicTransport", OptionOf(Scalar()))
    def provider[A](innerSelection: SelectionBuilder[FlinksterProvider, A]): SelectionBuilder[FlinksterParkingArea, A] =
      _root_.caliban.client.SelectionBuilder.Field("provider", Obj(innerSelection))
    def `type`: SelectionBuilder[FlinksterParkingArea, String] =
      _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def position[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[FlinksterParkingArea, A] =
      _root_.caliban.client.SelectionBuilder.Field("position", Obj(innerSelection))
    def GeoJSON[A](innerSelection: SelectionBuilder[GeoJSON, A]): SelectionBuilder[FlinksterParkingArea, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("GeoJSON", OptionOf(Obj(innerSelection)))
  }

  type FlinksterProvider
  object FlinksterProvider {
    def url: SelectionBuilder[FlinksterProvider, String] = _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
    def areaId: SelectionBuilder[FlinksterProvider, Int] =
      _root_.caliban.client.SelectionBuilder.Field("areaId", Scalar())
    def networkIds: SelectionBuilder[FlinksterProvider, List[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("networkIds", ListOf(Scalar()))
  }

  type GeoFeature
  object GeoFeature {
    def `type`: SelectionBuilder[GeoFeature, String] = _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def properties[A](innerSelection: SelectionBuilder[GeoProperties, A]): SelectionBuilder[GeoFeature, A] =
      _root_.caliban.client.SelectionBuilder.Field("properties", Obj(innerSelection))
    def geometry[A](innerSelection: SelectionBuilder[GeoPolygon, A]): SelectionBuilder[GeoFeature, A] =
      _root_.caliban.client.SelectionBuilder.Field("geometry", Obj(innerSelection))
  }

  type GeoJSON
  object GeoJSON {
    def `type`: SelectionBuilder[GeoJSON, String] = _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def features[A](innerSelection: SelectionBuilder[GeoFeature, A]): SelectionBuilder[GeoJSON, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("features", ListOf(Obj(innerSelection)))
  }

  type GeoPolygon
  object GeoPolygon {
    def `type`: SelectionBuilder[GeoPolygon, String] = _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def coordinates: SelectionBuilder[GeoPolygon, List[Option[List[Option[List[Option[List[Option[Double]]]]]]]]] =
      _root_.caliban.client.SelectionBuilder
        .Field("coordinates", ListOf(OptionOf(ListOf(OptionOf(ListOf(OptionOf(ListOf(OptionOf(Scalar())))))))))
  }

  type GeoProperties
  object GeoProperties {
    def name: SelectionBuilder[GeoProperties, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
  }

  type Location
  object Location {
    def latitude: SelectionBuilder[Location, Double] =
      _root_.caliban.client.SelectionBuilder.Field("latitude", Scalar())
    def longitude: SelectionBuilder[Location, Double] =
      _root_.caliban.client.SelectionBuilder.Field("longitude", Scalar())
  }

  type MailingAddress
  object MailingAddress {
    def city: SelectionBuilder[MailingAddress, String] = _root_.caliban.client.SelectionBuilder.Field("city", Scalar())
    def zipcode: SelectionBuilder[MailingAddress, String] =
      _root_.caliban.client.SelectionBuilder.Field("zipcode", Scalar())
    def street: SelectionBuilder[MailingAddress, String] =
      _root_.caliban.client.SelectionBuilder.Field("street", Scalar())
  }

  type Nearby
  object Nearby {
    def stations[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[Station, A]
    )(
      implicit encoder0: ArgEncoder[Option[Int]],
      encoder1: ArgEncoder[Option[Int]]
    ): SelectionBuilder[Nearby, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "stations",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count, "Int")(encoder0), Argument("offset", offset, "Int")(encoder1))
      )
    def parkingSpaces[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[ParkingSpace, A]
    )(
      implicit encoder0: ArgEncoder[Option[Int]],
      encoder1: ArgEncoder[Option[Int]]
    ): SelectionBuilder[Nearby, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "parkingSpaces",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count, "Int")(encoder0), Argument("offset", offset, "Int")(encoder1))
      )
    def travelCenters[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[TravelCenter, A]
    )(
      implicit encoder0: ArgEncoder[Option[Int]],
      encoder1: ArgEncoder[Option[Int]]
    ): SelectionBuilder[Nearby, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "travelCenters",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count, "Int")(encoder0), Argument("offset", offset, "Int")(encoder1))
      )
    def flinksterCars[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[FlinksterCar, A]
    )(
      implicit encoder0: ArgEncoder[Option[Int]],
      encoder1: ArgEncoder[Option[Int]]
    ): SelectionBuilder[Nearby, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "flinksterCars",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count, "Int")(encoder0), Argument("offset", offset, "Int")(encoder1))
      )
    def bikes[A](count: Option[Int] = None, offset: Option[Int] = None)(
      innerSelection: SelectionBuilder[FlinksterBike, A]
    )(
      implicit encoder0: ArgEncoder[Option[Int]],
      encoder1: ArgEncoder[Option[Int]]
    ): SelectionBuilder[Nearby, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "bikes",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("count", count, "Int")(encoder0), Argument("offset", offset, "Int")(encoder1))
      )
  }

  type Occupancy
  object Occupancy {
    def validData: SelectionBuilder[Occupancy, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("validData", Scalar())
    def timestamp: SelectionBuilder[Occupancy, String] =
      _root_.caliban.client.SelectionBuilder.Field("timestamp", Scalar())
    def timeSegment: SelectionBuilder[Occupancy, String] =
      _root_.caliban.client.SelectionBuilder.Field("timeSegment", Scalar())
    def category: SelectionBuilder[Occupancy, Int] = _root_.caliban.client.SelectionBuilder.Field("category", Scalar())
    def text: SelectionBuilder[Occupancy, String]  = _root_.caliban.client.SelectionBuilder.Field("text", Scalar())
  }

  type OpeningTime
  object OpeningTime {
    def from: SelectionBuilder[OpeningTime, String] = _root_.caliban.client.SelectionBuilder.Field("from", Scalar())
    def to: SelectionBuilder[OpeningTime, String]   = _root_.caliban.client.SelectionBuilder.Field("to", Scalar())
  }

  type OpeningTimes
  object OpeningTimes {
    def monday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("monday", OptionOf(Obj(innerSelection)))
    def tuesday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("tuesday", OptionOf(Obj(innerSelection)))
    def wednesday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("wednesday", OptionOf(Obj(innerSelection)))
    def thursday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("thursday", OptionOf(Obj(innerSelection)))
    def friday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("friday", OptionOf(Obj(innerSelection)))
    def saturday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("saturday", OptionOf(Obj(innerSelection)))
    def sunday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("sunday", OptionOf(Obj(innerSelection)))
    def holiday[A](innerSelection: SelectionBuilder[OpeningTime, A]): SelectionBuilder[OpeningTimes, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("holiday", OptionOf(Obj(innerSelection)))
  }

  type OperationLocation
  object OperationLocation {
    def id: SelectionBuilder[OperationLocation, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def abbrev: SelectionBuilder[OperationLocation, String] =
      _root_.caliban.client.SelectionBuilder.Field("abbrev", Scalar())
    def name: SelectionBuilder[OperationLocation, String] =
      _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def shortName: SelectionBuilder[OperationLocation, String] =
      _root_.caliban.client.SelectionBuilder.Field("shortName", Scalar())
    def `type`: SelectionBuilder[OperationLocation, String] =
      _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def status: SelectionBuilder[OperationLocation, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("status", OptionOf(Scalar()))
    def locationCode: SelectionBuilder[OperationLocation, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("locationCode", OptionOf(Scalar()))
    def UIC: SelectionBuilder[OperationLocation, String] = _root_.caliban.client.SelectionBuilder.Field("UIC", Scalar())
    def regionId: SelectionBuilder[OperationLocation, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("regionId", OptionOf(Scalar()))
    def validFrom: SelectionBuilder[OperationLocation, String] =
      _root_.caliban.client.SelectionBuilder.Field("validFrom", Scalar())
    def validTill: SelectionBuilder[OperationLocation, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("validTill", OptionOf(Scalar()))
    def timeTableRelevant: SelectionBuilder[OperationLocation, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("timeTableRelevant", OptionOf(Scalar()))
    def borderStation: SelectionBuilder[OperationLocation, Option[Boolean]] =
      _root_.caliban.client.SelectionBuilder.Field("borderStation", OptionOf(Scalar()))
  }

  type ParkingPriceOption
  object ParkingPriceOption {
    def id: SelectionBuilder[ParkingPriceOption, Int] = _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def duration: SelectionBuilder[ParkingPriceOption, String] =
      _root_.caliban.client.SelectionBuilder.Field("duration", Scalar())
    def price: SelectionBuilder[ParkingPriceOption, Option[Double]] =
      _root_.caliban.client.SelectionBuilder.Field("price", OptionOf(Scalar()))
  }

  type ParkingSpace
  object ParkingSpace {
    def `type`: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("type", OptionOf(Scalar()))
    def id: SelectionBuilder[ParkingSpace, Int] = _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def name: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def label: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("label", OptionOf(Scalar()))
    def spaceNumber: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("spaceNumber", OptionOf(Scalar()))
    def responsibility: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("responsibility", OptionOf(Scalar()))
    def source: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("source", OptionOf(Scalar()))
    def nameDisplay: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("nameDisplay", OptionOf(Scalar()))
    def spaceType: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("spaceType", OptionOf(Scalar()))
    def spaceTypeEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("spaceTypeEn", OptionOf(Scalar()))
    def spaceTypeName: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("spaceTypeName", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("location", OptionOf(Obj(innerSelection)))
    def url: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("url", OptionOf(Scalar()))
    def operator: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("operator", OptionOf(Scalar()))
    def operatorUrl: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("operatorUrl", OptionOf(Scalar()))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("address", OptionOf(Obj(innerSelection)))
    def distance: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("distance", OptionOf(Scalar()))
    def facilityType: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("facilityType", OptionOf(Scalar()))
    def facilityTypeEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("facilityTypeEn", OptionOf(Scalar()))
    def openingHours: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("openingHours", OptionOf(Scalar()))
    def openingHoursEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("openingHoursEn", OptionOf(Scalar()))
    def numberParkingPlaces: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("numberParkingPlaces", OptionOf(Scalar()))
    def numberHandicapedPlaces: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("numberHandicapedPlaces", OptionOf(Scalar()))
    def isSpecialProductDb: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isSpecialProductDb", Scalar())
    def isOutOfService: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isOutOfService", Scalar())
    def station[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("station", OptionOf(Obj(innerSelection)))
    def occupancy[A](innerSelection: SelectionBuilder[Occupancy, A]): SelectionBuilder[ParkingSpace, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("occupancy", OptionOf(Obj(innerSelection)))
    def outOfServiceText: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("outOfServiceText", OptionOf(Scalar()))
    def outOfServiceTextEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("outOfServiceTextEn", OptionOf(Scalar()))
    def reservation: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("reservation", OptionOf(Scalar()))
    def clearanceWidth: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("clearanceWidth", OptionOf(Scalar()))
    def clearanceHeight: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("clearanceHeight", OptionOf(Scalar()))
    def allowedPropulsions: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("allowedPropulsions", OptionOf(Scalar()))
    def hasChargingStation: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("hasChargingStation", OptionOf(Scalar()))
    def tariffPrices[A](
      innerSelection: SelectionBuilder[ParkingPriceOption, A]
    ): SelectionBuilder[ParkingSpace, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffPrices", ListOf(Obj(innerSelection)))
    def outOfService: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("outOfService", Scalar())
    def isDiscountDbBahnCard: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isDiscountDbBahnCard", Scalar())
    def isMonthVendingMachine: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isMonthVendingMachine", Scalar())
    def isDiscountDbBahnComfort: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isDiscountDbBahnComfort", Scalar())
    def isDiscountDbParkAndRail: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isDiscountDbParkAndRail", Scalar())
    def isMonthParkAndRide: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isMonthParkAndRide", Scalar())
    def isMonthSeason: SelectionBuilder[ParkingSpace, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("isMonthSeason", Scalar())
    def tariffDiscount: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffDiscount", OptionOf(Scalar()))
    def tariffFreeParkingTime: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffFreeParkingTime", OptionOf(Scalar()))
    def tariffDiscountEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffDiscountEn", OptionOf(Scalar()))
    def tariffPaymentOptions: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffPaymentOptions", OptionOf(Scalar()))
    def tariffPaymentCustomerCards: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffPaymentCustomerCards", OptionOf(Scalar()))
    def tariffFreeParkingTimeEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffFreeParkingTimeEn", OptionOf(Scalar()))
    def tariffPaymentOptionsEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("tariffPaymentOptionsEn", OptionOf(Scalar()))
    def slogan: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("slogan", OptionOf(Scalar()))
    def sloganEn: SelectionBuilder[ParkingSpace, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("sloganEn", OptionOf(Scalar()))
  }

  type Photographer
  object Photographer {
    def name: SelectionBuilder[Photographer, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def url: SelectionBuilder[Photographer, String]  = _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
  }

  type Picture
  object Picture {
    def id: SelectionBuilder[Picture, Int]         = _root_.caliban.client.SelectionBuilder.Field("id", Scalar())
    def url: SelectionBuilder[Picture, String]     = _root_.caliban.client.SelectionBuilder.Field("url", Scalar())
    def license: SelectionBuilder[Picture, String] = _root_.caliban.client.SelectionBuilder.Field("license", Scalar())
    def photographer[A](innerSelection: SelectionBuilder[Photographer, A]): SelectionBuilder[Picture, A] =
      _root_.caliban.client.SelectionBuilder.Field("photographer", Obj(innerSelection))
  }

  type PriceOption
  object PriceOption {
    def interval: SelectionBuilder[PriceOption, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("interval", OptionOf(Scalar()))
    def `type`: SelectionBuilder[PriceOption, String] = _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def grossamount: SelectionBuilder[PriceOption, Double] =
      _root_.caliban.client.SelectionBuilder.Field("grossamount", Scalar())
    def currency: SelectionBuilder[PriceOption, String] =
      _root_.caliban.client.SelectionBuilder.Field("currency", Scalar())
    def taxrate: SelectionBuilder[PriceOption, Double] =
      _root_.caliban.client.SelectionBuilder.Field("taxrate", Scalar())
    def preferredprice: SelectionBuilder[PriceOption, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("preferredprice", Scalar())
  }

  type Product
  object Product {
    def name: SelectionBuilder[Product, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def `class`: SelectionBuilder[Product, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("class", OptionOf(Scalar()))
    def productCode: SelectionBuilder[Product, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("productCode", OptionOf(Scalar()))
    def productName: SelectionBuilder[Product, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("productName", OptionOf(Scalar()))
  }

  type RegionalArea
  object RegionalArea {
    def number: SelectionBuilder[RegionalArea, Int]  = _root_.caliban.client.SelectionBuilder.Field("number", Scalar())
    def name: SelectionBuilder[RegionalArea, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def shortName: SelectionBuilder[RegionalArea, String] =
      _root_.caliban.client.SelectionBuilder.Field("shortName", Scalar())
  }

  type Route
  object Route {
    def parts[A](innerSelection: SelectionBuilder[RoutePart, A]): SelectionBuilder[Route, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("parts", ListOf(Obj(innerSelection)))
    def from[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Route, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("from", OptionOf(Obj(innerSelection)))
    def to[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Route, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("to", OptionOf(Obj(innerSelection)))
  }

  type RoutePart
  object RoutePart {

    /**
     * Station where the part begins
     */
    def from[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RoutePart, A] =
      _root_.caliban.client.SelectionBuilder.Field("from", Obj(innerSelection))
    def to[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[RoutePart, A] =
      _root_.caliban.client.SelectionBuilder.Field("to", Obj(innerSelection))
    def delay: SelectionBuilder[RoutePart, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("delay", OptionOf(Scalar()))
    def product[A](innerSelection: SelectionBuilder[Product, A]): SelectionBuilder[RoutePart, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("product", OptionOf(Obj(innerSelection)))
    def direction: SelectionBuilder[RoutePart, String] =
      _root_.caliban.client.SelectionBuilder.Field("direction", Scalar())
    def start: SelectionBuilder[RoutePart, String] = _root_.caliban.client.SelectionBuilder.Field("start", Scalar())
    def end: SelectionBuilder[RoutePart, String]   = _root_.caliban.client.SelectionBuilder.Field("end", Scalar())
    def departingTrack[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[RoutePart, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("departingTrack", OptionOf(Obj(innerSelection)))
    def arrivingTrack[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[RoutePart, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("arrivingTrack", OptionOf(Obj(innerSelection)))
  }

  type Searchable
  object Searchable {
    def stations[A](innerSelection: SelectionBuilder[Station, A]): SelectionBuilder[Searchable, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("stations", ListOf(Obj(innerSelection)))
    def operationLocations[A](
      innerSelection: SelectionBuilder[OperationLocation, A]
    ): SelectionBuilder[Searchable, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("operationLocations", ListOf(Obj(innerSelection)))
  }

  type Station
  object Station {
    def primaryEvaId: SelectionBuilder[Station, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("primaryEvaId", OptionOf(Scalar()))
    def stationNumber: SelectionBuilder[Station, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("stationNumber", OptionOf(Scalar()))
    def primaryRil100: SelectionBuilder[Station, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("primaryRil100", OptionOf(Scalar()))
    def name: SelectionBuilder[Station, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[Station, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("location", OptionOf(Obj(innerSelection)))
    def category: SelectionBuilder[Station, Int] = _root_.caliban.client.SelectionBuilder.Field("category", Scalar())
    def priceCategory: SelectionBuilder[Station, Int] =
      _root_.caliban.client.SelectionBuilder.Field("priceCategory", Scalar())
    def hasParking: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasParking", Scalar())
    def hasBicycleParking: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasBicycleParking", Scalar())
    def hasLocalPublicTransport: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasLocalPublicTransport", Scalar())
    def hasPublicFacilities: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasPublicFacilities", Scalar())
    def hasLockerSystem: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasLockerSystem", Scalar())
    def hasTaxiRank: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasTaxiRank", Scalar())
    def hasTravelNecessities: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasTravelNecessities", Scalar())
    def hasSteplessAccess: SelectionBuilder[Station, String] =
      _root_.caliban.client.SelectionBuilder.Field("hasSteplessAccess", Scalar())
    def hasMobilityService: SelectionBuilder[Station, String] =
      _root_.caliban.client.SelectionBuilder.Field("hasMobilityService", Scalar())
    def federalState: SelectionBuilder[Station, String] =
      _root_.caliban.client.SelectionBuilder.Field("federalState", Scalar())
    def regionalArea[A](innerSelection: SelectionBuilder[RegionalArea, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("regionalArea", Obj(innerSelection))
    def facilities[A](innerSelection: SelectionBuilder[Facility, A]): SelectionBuilder[Station, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("facilities", ListOf(Obj(innerSelection)))
    def mailingAddress[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("mailingAddress", Obj(innerSelection))
    def DBInformationOpeningTimes[A](
      innerSelection: SelectionBuilder[OpeningTimes, A]
    ): SelectionBuilder[Station, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("DBInformationOpeningTimes", OptionOf(Obj(innerSelection)))
    def localServiceStaffAvailability[A](
      innerSelection: SelectionBuilder[OpeningTimes, A]
    ): SelectionBuilder[Station, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("localServiceStaffAvailability", OptionOf(Obj(innerSelection)))
    def aufgabentraeger[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("aufgabentraeger", Obj(innerSelection))
    def timeTableOffice[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("timeTableOffice", OptionOf(Obj(innerSelection)))
    def szentrale[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("szentrale", Obj(innerSelection))
    def stationManagement[A](innerSelection: SelectionBuilder[StationContact, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("stationManagement", Obj(innerSelection))
    def timetable[A](innerSelection: SelectionBuilder[Timetable, A]): SelectionBuilder[Station, A] =
      _root_.caliban.client.SelectionBuilder.Field("timetable", Obj(innerSelection))
    def parkingSpaces[A](innerSelection: SelectionBuilder[ParkingSpace, A]): SelectionBuilder[Station, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("parkingSpaces", ListOf(Obj(innerSelection)))
    def hasSteamPermission: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasSteamPermission", Scalar())
    def hasWiFi: SelectionBuilder[Station, Boolean] = _root_.caliban.client.SelectionBuilder.Field("hasWiFi", Scalar())
    def hasTravelCenter: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasTravelCenter", Scalar())
    def hasRailwayMission: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasRailwayMission", Scalar())
    def hasDBLounge: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasDBLounge", Scalar())
    def hasLostAndFound: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasLostAndFound", Scalar())
    def hasCarRental: SelectionBuilder[Station, Boolean] =
      _root_.caliban.client.SelectionBuilder.Field("hasCarRental", Scalar())
    def tracks[A](innerSelection: SelectionBuilder[Track, A]): SelectionBuilder[Station, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("tracks", ListOf(Obj(innerSelection)))
    def picture[A](innerSelection: SelectionBuilder[Picture, A]): SelectionBuilder[Station, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("picture", OptionOf(Obj(innerSelection)))
  }

  type StationContact
  object StationContact {
    def name: SelectionBuilder[StationContact, String] = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())
    def shortName: SelectionBuilder[StationContact, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("shortName", OptionOf(Scalar()))
    def email: SelectionBuilder[StationContact, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("email", OptionOf(Scalar()))
    def number: SelectionBuilder[StationContact, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("number", OptionOf(Scalar()))
    def phoneNumber: SelectionBuilder[StationContact, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("phoneNumber", OptionOf(Scalar()))
  }

  type Timetable
  object Timetable {
    def nextArrivals[A](innerSelection: SelectionBuilder[TrainInStation, A]): SelectionBuilder[Timetable, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("nextArrivals", ListOf(Obj(innerSelection)))
    def nextDepatures[A](innerSelection: SelectionBuilder[TrainInStation, A]): SelectionBuilder[Timetable, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field("nextDepatures", ListOf(Obj(innerSelection)))
  }

  type Track
  object Track {
    def platform: SelectionBuilder[Track, String] = _root_.caliban.client.SelectionBuilder.Field("platform", Scalar())
    def number: SelectionBuilder[Track, String]   = _root_.caliban.client.SelectionBuilder.Field("number", Scalar())
    def name: SelectionBuilder[Track, String]     = _root_.caliban.client.SelectionBuilder.Field("name", Scalar())

    /**
     * Length of the platform in cm
     */
    def length: SelectionBuilder[Track, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("length", OptionOf(Scalar()))

    /**
     * Height of the platform in cm
     */
    def height: SelectionBuilder[Track, Int] = _root_.caliban.client.SelectionBuilder.Field("height", Scalar())
  }

  type TrainInStation
  object TrainInStation {
    def `type`: SelectionBuilder[TrainInStation, String] =
      _root_.caliban.client.SelectionBuilder.Field("type", Scalar())
    def trainNumber: SelectionBuilder[TrainInStation, String] =
      _root_.caliban.client.SelectionBuilder.Field("trainNumber", Scalar())
    def platform: SelectionBuilder[TrainInStation, String] =
      _root_.caliban.client.SelectionBuilder.Field("platform", Scalar())
    def time: SelectionBuilder[TrainInStation, String] = _root_.caliban.client.SelectionBuilder.Field("time", Scalar())
    def stops: SelectionBuilder[TrainInStation, List[String]] =
      _root_.caliban.client.SelectionBuilder.Field("stops", ListOf(Scalar()))
  }

  type TravelCenter
  object TravelCenter {
    def id: SelectionBuilder[TravelCenter, Option[Int]] =
      _root_.caliban.client.SelectionBuilder.Field("id", OptionOf(Scalar()))
    def name: SelectionBuilder[TravelCenter, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("name", OptionOf(Scalar()))
    def address[A](innerSelection: SelectionBuilder[MailingAddress, A]): SelectionBuilder[TravelCenter, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("address", OptionOf(Obj(innerSelection)))
    def `type`: SelectionBuilder[TravelCenter, Option[String]] =
      _root_.caliban.client.SelectionBuilder.Field("type", OptionOf(Scalar()))
    def location[A](innerSelection: SelectionBuilder[Location, A]): SelectionBuilder[TravelCenter, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field("location", OptionOf(Obj(innerSelection)))
  }

  type Query = _root_.caliban.client.Operations.RootQuery
  object Query {
    def routing[A](from: Int, to: Int)(innerSelection: SelectionBuilder[Route, A])(
      implicit encoder0: ArgEncoder[Int],
      encoder1: ArgEncoder[Int]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, List[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "routing",
        ListOf(Obj(innerSelection)),
        arguments = List(Argument("from", from, "Int!")(encoder0), Argument("to", to, "Int!")(encoder1))
      )
    def stationWithEvaId[A](evaId: Int)(
      innerSelection: SelectionBuilder[Station, A]
    )(implicit encoder0: ArgEncoder[Int]): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "stationWithEvaId",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("evaId", evaId, "Int!")(encoder0))
      )
    def stationWithStationNumber[A](stationNumber: Int)(
      innerSelection: SelectionBuilder[Station, A]
    )(implicit encoder0: ArgEncoder[Int]): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "stationWithStationNumber",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("stationNumber", stationNumber, "Int!")(encoder0))
      )
    def stationWithRill100[A](rill100: String)(
      innerSelection: SelectionBuilder[Station, A]
    )(implicit encoder0: ArgEncoder[String]): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder.Field(
        "stationWithRill100",
        OptionOf(Obj(innerSelection)),
        arguments = List(Argument("rill100", rill100, "String!")(encoder0))
      )
    def search[A](searchTerm: Option[String] = None)(
      innerSelection: SelectionBuilder[Searchable, A]
    )(implicit encoder0: ArgEncoder[Option[String]]): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, A] =
      _root_.caliban.client.SelectionBuilder
        .Field("search", Obj(innerSelection), arguments = List(Argument("searchTerm", searchTerm, "String")(encoder0)))
    def nearby[A](latitude: Double, longitude: Double, radius: Option[Int] = None)(
      innerSelection: SelectionBuilder[Nearby, A]
    )(
      implicit encoder0: ArgEncoder[Double],
      encoder1: ArgEncoder[Double],
      encoder2: ArgEncoder[Option[Int]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, A] =
      _root_.caliban.client.SelectionBuilder.Field(
        "nearby",
        Obj(innerSelection),
        arguments = List(
          Argument("latitude", latitude, "Float!")(encoder0),
          Argument("longitude", longitude, "Float!")(encoder1),
          Argument("radius", radius, "Int")(encoder2)
        )
      )
    def parkingSpace[A](id: Option[Int] = None)(innerSelection: SelectionBuilder[ParkingSpace, A])(
      implicit encoder0: ArgEncoder[Option[Int]]
    ): SelectionBuilder[_root_.caliban.client.Operations.RootQuery, Option[A]] =
      _root_.caliban.client.SelectionBuilder
        .Field("parkingSpace", OptionOf(Obj(innerSelection)), arguments = List(Argument("id", id, "Int")(encoder0)))
  }

}
