'use client';

import { useEffect, useState } from 'react';
import { eventApi } from '@/lib/api';
import EventCard from './EventCard';

export default function EventList() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState<string>('all');

  useEffect(() => {
    fetchEvents();
  }, [filter]);

  const fetchEvents = async () => {
    setLoading(true);
    try {
      let response;
      if (filter === 'all') {
        response = await eventApi.getAll();
      } else if (filter === 'upcoming') {
        response = await eventApi.getUpcoming();
      } else {
        response = await eventApi.getByType(filter);
      }
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching events:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-8">
      {/* Filter Buttons */}
      <div className="bg-white/70 backdrop-blur-sm rounded-2xl p-6 shadow-xl border border-white/30">
        <h3 className="text-lg font-semibold text-gray-800 mb-4">Filter Events</h3>
        <div className="flex flex-wrap gap-3">
          <button
            onClick={() => setFilter('all')}
            className={`px-6 py-3 rounded-xl font-semibold transition-all duration-300 transform hover:scale-105 ${
              filter === 'all' 
                ? 'bg-gradient-to-r from-blue-500 to-slate-500 text-white shadow-lg' 
                : 'bg-white/80 text-gray-700 hover:bg-white shadow-md border border-gray-200'
            }`}
          >
            <span className="flex items-center">
              <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14-7H3a2 2 0 00-2 2v12a2 2 0 002 2h16a2 2 0 002-2V6a2 2 0 00-2-2z" />
              </svg>
              All Events
            </span>
          </button>
          
          <button
            onClick={() => setFilter('upcoming')}
            className={`px-6 py-3 rounded-xl font-semibold transition-all duration-300 transform hover:scale-105 ${
              filter === 'upcoming' 
                ? 'bg-gradient-to-r from-slate-500 to-blue-500 text-white shadow-lg' 
                : 'bg-white/80 text-gray-700 hover:bg-white shadow-md border border-gray-200'
            }`}
          >
            <span className="flex items-center">
              <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              Upcoming
            </span>
          </button>
          
          <button
            onClick={() => setFilter('EVENT')}
            className={`px-6 py-3 rounded-xl font-semibold transition-all duration-300 transform hover:scale-105 ${
              filter === 'EVENT' 
                ? 'bg-gradient-to-r from-blue-500 to-slate-500 text-white shadow-lg' 
                : 'bg-white/80 text-gray-700 hover:bg-white shadow-md border border-gray-200'
            }`}
          >
            <span className="flex items-center">
              <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              Events
            </span>
          </button>
          
          <button
            onClick={() => setFilter('TRIP')}
            className={`px-6 py-3 rounded-xl font-semibold transition-all duration-300 transform hover:scale-105 ${
              filter === 'TRIP' 
                ? 'bg-gradient-to-r from-slate-500 to-blue-500 text-white shadow-lg' 
                : 'bg-white/80 text-gray-700 hover:bg-white shadow-md border border-gray-200'
            }`}
          >
            <span className="flex items-center">
              <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              Trips
            </span>
          </button>
        </div>
      </div>

      {/* Loading State */}
      {loading ? (
        <div className="text-center py-16">
          <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-500 to-slate-500 rounded-full shadow-lg mb-4">
            <svg className="animate-spin h-8 w-8 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
              <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
          </div>
          <p className="text-xl font-semibold text-gray-700">Loading amazing events...</p>
        </div>
      ) : events.length === 0 ? (
        <div className="text-center py-16">
          <div className="w-24 h-24 mx-auto mb-6 bg-gradient-to-br from-gray-200 to-gray-300 rounded-2xl flex items-center justify-center">
            <svg className="w-12 h-12 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-6-4h6m2 5.291A7.962 7.962 0 0118 12a8 8 0 10-16 0 7.962 7.962 0 016 5.291z" />
            </svg>
          </div>
          <h3 className="text-2xl font-bold text-gray-800 mb-2">No Events Found</h3>
          <p className="text-gray-600">Try adjusting your filters or check back later for new events.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {events.map((event) => (
            <EventCard key={event.id} event={event} onUpdate={fetchEvents} />
          ))}
        </div>
      )}
    </div>
  );
}
