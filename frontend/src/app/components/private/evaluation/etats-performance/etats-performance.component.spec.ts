import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtatsPerformanceComponent } from './etats-performance.component';

describe('EtatsPerformanceComponent', () => {
  let component: EtatsPerformanceComponent;
  let fixture: ComponentFixture<EtatsPerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtatsPerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtatsPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
