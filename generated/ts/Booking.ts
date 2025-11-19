/**
 * Generated from PlantUML diagram
 * @generated
 */
export interface Booking {
  id: number;
  userId: number;
  eventId: number;
  bookingCode: string;
  numberOfSeats: number;
  totalAmount: number;
  status: string;
  paymentMethod: string;
  bookingDate: string;
  validated: boolean;
}
