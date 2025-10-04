import Link from 'next/link';

export default function Home() {
  return (
    <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center">
      <div className="text-center space-y-8 px-4">
        <h1 className="text-5xl font-bold text-gray-900">
          Welcome to AIU Events & Trips
        </h1>
        <p className="text-xl text-gray-600 max-w-2xl mx-auto">
          Discover and book exciting university events and trips. 
          Join your fellow students for unforgettable experiences!
        </p>
        <div className="flex gap-4 justify-center">
          <Link
            href="/events"
            className="bg-blue-600 text-white px-8 py-3 rounded-lg hover:bg-blue-700 transition-colors text-lg font-semibold"
          >
            Browse Events
          </Link>
          <Link
            href="/register"
            className="bg-gray-200 text-gray-800 px-8 py-3 rounded-lg hover:bg-gray-300 transition-colors text-lg font-semibold"
          >
            Get Started
          </Link>
        </div>
        
        <div className="mt-16 grid grid-cols-1 md:grid-cols-3 gap-8 max-w-4xl mx-auto">
          <div className="p-6 bg-white rounded-lg shadow-md">
            <div className="text-blue-600 text-4xl mb-4">🎉</div>
            <h3 className="text-lg font-semibold mb-2">Easy Booking</h3>
            <p className="text-gray-600">Book your spot with just a few clicks</p>
          </div>
          <div className="p-6 bg-white rounded-lg shadow-md">
            <div className="text-blue-600 text-4xl mb-4">📱</div>
            <h3 className="text-lg font-semibold mb-2">QR Code Entry</h3>
            <p className="text-gray-600">Get your QR code for easy event check-in</p>
          </div>
          <div className="p-6 bg-white rounded-lg shadow-md">
            <div className="text-blue-600 text-4xl mb-4">🔔</div>
            <h3 className="text-lg font-semibold mb-2">Stay Updated</h3>
            <p className="text-gray-600">Receive notifications about your bookings</p>
          </div>
        </div>
      </div>
    </div>
  );
}

