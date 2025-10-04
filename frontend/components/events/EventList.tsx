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
    <div className="space-y-6">
      <div className="flex gap-2">
        <button
          onClick={() => setFilter('all')}
          className={`px-4 py-2 rounded-md ${filter === 'all' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'}`}
        >
          All
        </button>
        <button
          onClick={() => setFilter('upcoming')}
          className={`px-4 py-2 rounded-md ${filter === 'upcoming' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'}`}
        >
          Upcoming
        </button>
        <button
          onClick={() => setFilter('EVENT')}
          className={`px-4 py-2 rounded-md ${filter === 'EVENT' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'}`}
        >
          Events
        </button>
        <button
          onClick={() => setFilter('TRIP')}
          className={`px-4 py-2 rounded-md ${filter === 'TRIP' ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-700'}`}
        >
          Trips
        </button>
      </div>

      {loading ? (
        <div className="text-center py-8">Loading...</div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {events.map((event) => (
            <EventCard key={event.id} event={event} onUpdate={fetchEvents} />
          ))}
        </div>
      )}
    </div>
  );
}
